<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="page.processDefinitionKey" :placeholder="$t('core.process.columns.processDefinitionKey')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-input v-model="page.displayName" :placeholder="$t('columns.displayName')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-select v-model="page.status" :placeholder="$t('columns.status')" multiple clearable collapse-tags class="filter-item search-select">
        <el-option v-for="item in instanceStatuses" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search" />
      <el-button :disabled="retryDisabled" class="filter-item" type="warning" icon="el-icon-s-operation" @click="retry">{{ $t('actions.retry') }}</el-button>
      <el-button :disabled="terminateDisabled" class="filter-item" type="danger" icon="el-icon-stopwatch" @click="terminate">{{ $t('actions.terminate') }}</el-button>
      <div style="float: right">
        <el-popover
          placement="bottom"
          trigger="hover"
        >
          <el-button :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('actions.exportList') }}</el-button>
          <el-button slot="reference" class="filter-item table-external-button" type="warning">{{ $t('actions.highOperation') }}</el-button>
        </el-popover>
        <el-popover
          placement="bottom"
          trigger="hover"
        >
          <el-form
            label-position="left"
            label-width="100px"
            style="height: 100%"
          >
            <el-form-item :label="$t('columns.startTime')" style="margin-bottom: 5px;">
              <date-time-generator v-model="page.lowerStartTime" @update="page.lowerStartTime = $event" @change="search" />
            </el-form-item>
            <el-form-item>
              <date-time-generator v-model="page.upperStartTime" @update="page.upperStartTime = $event" @change="search" />
            </el-form-item>
            <el-form-item :label="$t('columns.endTime')" style="margin-bottom: 5px;">
              <date-time-generator v-model="page.lowerEndTime" @update="page.lowerEndTime = $event" @change="search" />
            </el-form-item>
            <el-form-item>
              <date-time-generator v-model="page.upperEndTime" @update="page.upperEndTime = $event" @change="search" />
            </el-form-item>
          </el-form>
          <el-button slot="reference" class="filter-item table-external-button" type="primary">{{ $t('actions.highSearch') }}</el-button>
        </el-popover>
      </div>
    </div>

    <el-table
      ref="tables"
      v-loading="listLoading"
      :data="list"
      row-key="processInstanceId"
      border
      fit
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      highlight-current-row
      style="width: 100%;"
      lazy
      :load="load"
      @selection-change="handleSelectionChange"
      @sort-change="sortChange"
      @cell-click="clickCell"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item :label="$t('core.process.columns.version')">
              <span>{{ props.row.version }}</span>
            </el-form-item>
            <el-form-item :label="$t('monitor.columns.starter')">
              <span>{{ props.row.starter }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.startTime')">
              <span v-show="props.row.startTime">{{ props.row.startTime | parseTime }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.endTime')">
              <span v-show="props.row.endTime">{{ props.row.endTime | parseTime }}</span>
            </el-form-item>
            <el-form-item :label="$t('monitor.columns.duration')">
              <span v-show="props.row.duration">{{ props.row.duration | parseDuration }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.granter')">
              <span>{{ props.row.granters }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.grantName')">
              <span>{{ props.row.grantNames }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.assignee')">
              <span>{{ props.row.assignees }}</span>
            </el-form-item>
            <el-form-item :label="$t('columns.actId')">
              <span>{{ props.row.actId }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        type="selection"
        width="45"
      />
      <el-table-column :label="$t('core.process.columns.processDefinitionKey')" show-overflow-tooltip min-width="120px">
        <template slot-scope="scope">
          <el-link class="el-icon-link" :type="scope.row.status | renderInstanceStatus" @click="openTrace(scope.row)">{{ scope.row.processDefinitionKey }}</el-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.displayName')" min-width="120px">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.displayName || scope.row.processDefinitionKey }}</p>
            <div slot="reference">
              <span class="single-line">{{ scope.row.displayName || scope.row.processDefinitionKey }}</span>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.assignName')" show-overflow-tooltip align="center" class-name="status-col" width="100px">
        <template slot-scope="scope">
          <span>{{ scope.row.assignNames }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.actName')" show-overflow-tooltip align="center" class-name="status-col" width="100px">
        <template slot-scope="scope">
          <span>{{ scope.row.actName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.assignTime')" width="160px" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.assignTime | parseTime }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="page.total>0"
      :total="page.total"
      :page.sync="page.currentPage"
      :limit.sync="page.pageSize"
      @pagination="search"
    />
  </div>
</template>

<script>
import { listReq, listRetryReq, listChildrenReq, listRetryChildrenReq, retryReq, terminateReq } from '@/api/instance/instance'
import { getTimeStr } from '@/utils/tools'
import Pagination from '@/components/Pagination'
import DateTimeGenerator from '@/components/DateTimeGenerator'

export default {
  name: 'Monitor',
  components: {
    DateTimeGenerator,
    Pagination
  },
  props: {
    monitorType: {
      type: String,
      default: 'total'
    }
  },
  data() {
    return {
      list: [],
      listLoading: true,
      page: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
        processDefinitionKey: undefined,
        displayName: undefined,
        lowerStartTime: null,
        upperStartTime: null,
        lowerEndTime: null,
        upperEndTime: null,
        status: [],
        sort: 'START_TIME DESC',
        listChildren: true
      },
      doingInstanceStatuses: [],
      doneInstanceStatuses: [],
      instanceStatuses: [],
      instance: null,
      downloadLoading: false,
      selections: []
    }
  },
  computed: {
    retryDisabled() {
      return this.selections.length !== 1 || this.doneInstanceStatuses.find(item => { return item.value === this.selections[0].status }) === undefined
    },
    terminateDisabled() {
      return this.selections.length === 0 || this.doingInstanceStatuses.find(item => { return item.value === this.selections[0].status }) === undefined
    }
  },
  created() {
    this.loadConst()
    /*
    if (this.monitorType === 'total') {
      this.timer = setInterval(() => {
        this.search()
      }, 5000)
    }
    */
  },
  beforeDestroy() {
    /*
    if (this.timer) {
      clearInterval(this.timer)
    }
    */
  },
  methods: {
    retry() {
      if (!this.selections[0].processInstanceId) {
        this.$message.info(this.$t('tip.flowNotStart'))
        return
      }
      this.$confirm(this.$t('tip.confirmMsg'), this.$t('tip.confirm'), { type: 'info' })
        .then(() => {
          retryReq({
            processInstanceId: this.instance.processInstanceId
          })
            .then(res => {
              this.$message.success(res.data.msg)
              this.search()
            })
        })
    },
    terminate() {
      this.$confirm(this.$t('tip.confirmMsg'), this.$t('tip.confirm'), { type: 'info' })
        .then(() => {
          terminateReq({ ids: this.selections.map(item => item.id) })
            .then(res => {
              this.$message.success(res.data.msg)
              this.search()
            })
            .catch(() => {
              this.search()
            })
        })
    },
    search() {
      this.list = []
      this.listLoading = true
      this.initPageStatus()
      let request
      if (this.monitorType === 'retry') {
        request = listRetryReq
      } else {
        request = listReq
      }
      setTimeout(() => {
        request(this.page).then(res => {
          this.list = res.data.result.list
          Object.assign(this.page, res.data.result.page)
          this.listLoading = false
        })
      }, 300)
    },
    load(row, treeNode, resolve) {
      this.selectRow(row)
      let request
      if (this.monitorType === 'retry') {
        request = listRetryChildrenReq
      } else {
        request = listChildrenReq
      }
      request(this.instance).then(res => {
        resolve(res.data.result)
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'startTime') {
        this.sortByName(order)
      }
    },
    sortByName(order) {
      if (order === 'ascending') {
        this.page.sort = 'START_TIME'
      } else {
        this.page.sort = 'START_TIME DESC'
      }
      this.search()
    },
    formatStatus(item) {
      for (let i = 0; i < this.instanceStatuses.length; i++) {
        const option = this.instanceStatuses[i]
        if (option.value === item) {
          return option.label
        }
      }
      return item
    },
    selectRow(row) {
      if (row && this.selections.length === 1 && this.selections[0].id === row.id) {
        return
      }
      this.$refs.tables.clearSelection()
      if (row && row.id) {
        this.$refs.tables.toggleRowSelection(row, true)
      }
      this.reset()
    },
    reset() {
      this.instance = Object.assign({}, this.selections[0])
    },
    handleSelectionChange(val) {
      this.selections = val
      if (this.selections.length === 1) {
        this.reset()
      }
    },
    initPageStatus() {
      if (this.page.status.length === 0) {
        this.page.status = this.instanceStatuses.map(item => item.value)
      }
    },
    openTrace(row) {
      const route = {
        name: 'trace',
        params: {
          key: row.processDefinitionKey,
          processInstanceId: row.processInstanceId,
          processDefinitionId: row.processDefinitionId
        }
      }
      this.$router.push(route)
    },
    clickCell(row, column, cell, event) {
      this.selectRow(row)
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const translatedHeader = [
          this.$t('core.process.columns.processDefinitionKey'),
          this.$t('columns.displayName'),
          this.$t('columns.status'),
          this.$t('columns.startTime'),
          this.$t('columns.endTime'),
          this.$t('monitor.columns.duration'),
          'code',
          'msg'
        ]
        const columnNames = ['processDefinitionKey', 'displayName', 'status', 'startTime', 'endTime', 'duration', 'code', 'msg']
        const data = this.formatJson(columnNames, this.list)
        excel.export_json_to_excel({
          header: translatedHeader,
          data,
          filename: 'instance_' + getTimeStr()
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'status') {
          return this.formatStatus(v[j])
        } else {
          return v[j]
        }
      }))
    },
    loadConst() {
      const lang = localStorage.getItem('language')
      import('@/lang/dict.js').then(array => {
        this.doingInstanceStatuses = array['doingInstanceStatuses_' + lang]
        this.doneInstanceStatuses = array['doneInstanceStatuses_' + lang]
        this.instanceStatuses = array['instanceStatuses_' + lang]
        this.search()
      })
    }
  }
}
</script>
