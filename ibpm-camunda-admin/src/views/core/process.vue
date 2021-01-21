<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="page.processDefinitionKey" :placeholder="$t('core.process.columns.processDefinitionKey')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-input v-model="page.processDefinitionName" :placeholder="$t('core.process.columns.processDefinitionName')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-select v-model="page.status" :placeholder="$t('columns.status')" multiple clearable collapse-tags class="filter-item search-select">
        <el-option v-for="item in processStatuses" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search" />
      <el-button class="filter-item table-external-button" type="success" icon="el-icon-plus" @click="add" />
      <el-button :disabled="!selections || !selections.length" class="filter-item table-external-button" type="danger" icon="el-icon-delete" @click="remove" />
      <el-button :disabled="!selections || !selections.length" class="filter-item table-external-button" type="warning" @click="start">
        <svg-icon icon-class="hand" />{{ $t('actions.start') }}
      </el-button>
      <div style="float: right">
        <el-popover placement="bottom" trigger="hover">
          <el-upload style="display: inline;" action="" accept="application/zip" :on-change="importModel" :auto-upload="false" :show-file-list="false">
            <el-button class="high-operation" type="success" icon="el-icon-upload2">{{ $t('actions.importModel') }}</el-button>
          </el-upload>
          <el-button class="high-operation" :disabled="!selections || !selections.length" type="primary" icon="el-icon-download" @click="exportModel">{{ $t('actions.exportModel') }}</el-button><br>
          <el-button class="high-operation" :disabled="!selections || !selections.length" type="success" icon="el-icon-circle-check" @click="enable">{{ $t('actions.enable') }}</el-button>
          <el-button class="high-operation" :disabled="!selections || !selections.length" type="danger" icon="el-icon-circle-close" @click="disable">{{ $t('actions.disable') }}</el-button><br>
          <el-button class="high-operation" :disabled="!page || !page.total || page.total <= 0" :loading="downloadLoading" type="primary" icon="el-icon-download" @click="handleDownload">{{ $t('actions.exportList') }}</el-button><br>
          <el-button class="high-operation" :disabled="!selections || !selections.length" type="primary" @click="publish">
            <svg-icon icon-class="publish" /> {{ $t('actions.publish') }}
          </el-button><br>
          <el-button class="high-operation" :disabled="!selections || selections.length !== 1" type="primary" @click="showChart">
            {{ $t('statistics.chart') }}
          </el-button>
          <el-button slot="reference" class="filter-item table-external-button" type="warning">{{ $t('actions.highOperation') }}</el-button>
        </el-popover>
      </div>
    </div>

    <el-table
      ref="tables"
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @selection-change="handleSelectionChange"
      @cell-click="clickCell"
      @sort-change="sortChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item :label="$t('columns.updateTime')">
              <span>{{ props.row.updateTime | parseTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        type="selection"
        width="45"
      />
      <el-table-column
        :label="$t('core.process.columns.processDefinitionKey')"
        prop="processDefinitionKey"
        sortable="custom"
        min-width="100px"
      >
        <template slot-scope="scope">
          <el-link :type="scope.row.status | renderProcessStatus" @click="openDesignDialog(scope.row)">{{ scope.row.processDefinitionKey || '---' }}</el-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('core.process.columns.processDefinitionName')" min-width="150px">
        <template slot-scope="scope">
          <el-link type="primary" @click="update(scope.row)">{{ scope.row.processDefinitionName || '---' }}</el-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.status')" align="center" class-name="status-col" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | renderProcessStatus">{{ formatStatus(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="page.total > 0"
      :total="page.total"
      :page.sync="page.currentPage"
      :limit.sync="page.pageSize"
      @pagination="search"
    />

    <el-dialog
      :title="editDialog.title"
      :visible.sync="editDialog.base.visible || editDialog.chart.visible"
      :center="true"
      :modal="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="close"
      :fullscreen="editDialog.chart.visible"
    >
      <div v-show="editDialog.base.visible">
        <edit-form ref="editForm" :process="process" @save="save" />
      </div>
      <div v-show="editDialog.chart.visible">
        <ChartArea ref="chartArea" :process-charts="processCharts" :process-name="process.processDefinitionKey" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReq, removeReq, addReq, updateReq, enableReq, disableReq, publishReq, startReq, importModelReq, exportModelReq } from '@/api/core/process'
import * as calendarApi from '@/api/core/calendar'
import { buildMsg, getTimeStr, download } from '@/utils/tools'
import Pagination from '@/components/Pagination'
import EditForm from './components/process/editForm'
import ChartArea from './components/process/chart/chartArea'

const DEF_OBJ = {
  processDefinitionKey: undefined,
  processDefinitionName: '',
  status: 0,
  remark: '',
  content: ''
}
export default {
  name: 'process',
  components: {
    Pagination,
    EditForm,
    ChartArea
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      listLoading: true,
      page: {
        currentPage: 1,
        pageSize: 10,
        total: 0,
        processDefinitionName: undefined,
        status: [],
        sort: 'PROC_DEF_KEY'
      },
      processStatuses: [],
      milliSecondTimeUnits: [],
      inDayTimeUnits: [],
      dayTimeUnit: [],
      overDayTimeUnits: [],
      daysOfWeek: [],
      showCreateTime: false,
      process: DEF_OBJ,
      editDialog: {
        oper: undefined,
        title: undefined,
        base: {
          visible: false
        },
        chart: {
          visible: false
        }
      },
      downloadLoading: false,
      selections: [],
      calendars: [],
      timeZones: [],
      jsonData: null,
      processCharts: null
    }
  },
  created() {
    this.loadConst()
    this.initCalendars()
  },
  methods: {
    search() {
      this.listLoading = true
      this.initPageStatus()
      listReq(Object.assign({ processDefinitionKey: this.$route.params.key }, this.page)).then(response => {
        this.list = response.data.result.list
        Object.assign(this.page, response.data.result.page)
        setTimeout(() => {
          this.listLoading = false
        }, 200)
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'processDefinitionKey') {
        this.sortByName(order)
      }
    },
    sortByName(order) {
      if (order === 'ascending') {
        this.page.sort = 'PROC_DEF_KEY'
      } else {
        this.page.sort = 'PROC_DEF_KEY DESC'
      }
      this.search()
    },
    reset() {
      if (this.editDialog.oper !== 'update' && (!this.selections || !this.selections.length || !this.selections[0].processDefinitionKey)) {
        this.process = Object.assign({}, DEF_OBJ)
      } else {
        this.process = Object.assign({}, this.selections[0])
      }
    },
    close() {
      this.editDialog.base.visible =
          this.editDialog.chart.visible = false
    },
    add() {
      this.editDialog.oper = 'add'
      this.selectRow(null)
      this.editDialog.title = this.$t('actions.add')
      this.editDialog.base.visible = true
      if (this.$refs.editForm) {
        this.$refs.editForm.clearValidate()
      }
    },
    update(row) {
      this.editDialog.oper = 'update'
      this.selectRow(row)
      this.editDialog.title = this.$t('actions.update')
      this.editDialog.base.visible = true
      if (this.$refs.editForm) {
        this.$refs.editForm.clearValidate()
      }
    },
    save(process) {
      this.process = process
      const request = (this.editDialog.oper === 'add' ? addReq(this.process) : updateReq(this.process))
      request.then(res => {
        this.$message.success(res.data.msg)
        this.editDialog.base.visible = false
        this.search()
      })
    },
    publish() {
      const processDefinitionKeys = this.ofKeys()
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          publishReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            this.$message.success(res.data.msg)
            this.search()
          })
        })
    },
    enable() {
      const processDefinitionKeys = this.ofKeys()
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          enableReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            this.$message.success(res.data.msg)
            this.search()
          })
        })
    },
    disable() {
      const processDefinitionKeys = this.ofKeys()
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          disableReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            this.$message.success(res.data.msg)
            this.search()
          })
        })
    },
    ofKeys() {
      return this.selections.map(sel => sel.processDefinitionKey)
    },
    remove() {
      const processDefinitionKeys = this.ofKeys()
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          removeReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            this.$message.success(res.data.msg)
            this.search()
          })
        })
    },
    formatStatus(item) {
      for (let i = 0; i < this.processStatuses.length; i++) {
        const option = this.processStatuses[i]
        if (option.value === item) {
          return option.label
        }
      }
      return item
    },
    selectRow(row) {
      if (row && this.selections.length === 1 && this.selections[0].processDefinitionKey === row.processDefinitionKey) {
        return
      }
      this.$refs.tables.clearSelection()
      if (row && (this.editDialog.oper === 'update' || row.processDefinitionKey)) {
        this.$refs.tables.toggleRowSelection(row, true)
      }
      this.reset()
    },
    handleSelectionChange(val) {
      this.selections = val
    },
    clickCell(row, column, cell, event) {
      this.selectRow(row)
    },
    importModel(file) {
      this.$confirm(this.$t('tip.confirm') + ' ' + file.name, this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          const data = new FormData()
          data.append('file', file.raw)
          importModelReq(data).then(res => {
            this.$message.success(res.data.msg)
            this.search()
          })
        })
    },
    exportModel() {
      const processDefinitionKeys = this.ofKeys()
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          const fileName = 'model_' + getTimeStr() + '.zip'
          exportModelReq({ processDefinitionKeys: processDefinitionKeys, fileName: fileName }).then((res) => {
            download(res.data, fileName)
          })
        })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const translatedHeader = [
          this.$t('core.process.columns.processDefinitionKey'),
          this.$t('core.process.columns.processDefinitionName'),
          this.$t('columns.status')
        ]
        const columnNames = ['processDefinitionKey', 'processDefinitionName', 'status']
        const data = this.formatJson(columnNames, this.selections.length ? this.selections : this.list)
        excel.export_json_to_excel({
          header: translatedHeader,
          data,
          filename: 'process_' + getTimeStr()
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
        this.processStatuses = array['processStatuses_' + lang]
        this.milliSecondTimeUnits = array['milliSecondTimeUnits_' + lang]
        this.inDayTimeUnits = array['inDayTimeUnits_' + lang]
        this.dayTimeUnit = array['dayTimeUnit_' + lang]
        this.overDayTimeUnits = array['overDayTimeUnits_' + lang]
        this.daysOfWeek = array['daysOfWeek_' + lang]
        this.processCharts = array['processCharts_' + lang]
        this.search()
      })
    },
    openDesignDialog(row) {
      this.editDialog.title = this.$t('core.process.actions.design')
      this.selectRow(row)
      this.openDesignProcessFlow()
    },
    openDesignProcessFlow() {
      const route = {
        name: 'flow',
        params: {
          key: this.selections[0].processDefinitionKey
        }
      }
      this.$router.push(route)
    },
    start() {
      if (this.selections.length === 1 && this.selections[0].status === 1) {
        this.$message.warning(this.$t('tip.disabledProcessError') + ':' + this.selections[0].processDefinitionKey)
        return
      }
      const processDefinitionKeys = []
      for (let i = 0; i < this.selections.length; i++) {
        const sel = this.selections[i]
        if (sel.status === 1) {
          this.$message.warning(this.$t('tip.disabledProcessError') + ':' + sel.processDefinitionKey)
          return
        }
        processDefinitionKeys.push(sel.processDefinitionKey)
      }
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          startReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            if (res.data.result) {
              this.$router.push({ name: res.data.result, replace: false })
            } else {
              this.$message.success(res.data.msg)
            }
          })
        })
    },
    showChart() {
      this.editDialog.title = this.$t('statistics.chart') + '-' + this.process.processDefinitionName + '(' + this.process.processDefinitionKey + ')'
      this.editDialog.chart.visible = true
      this.$nextTick(() => {
        if (this.$refs.chartArea) {
          this.$refs.chartArea.chartValue = this.processCharts[0].value
          this.$refs.chartArea.reRender()
        }
      })
    },
    initCalendars() {
      calendarApi.listReq().then(res => {
        this.calendars = res.data.result
      })
    },
    initPageStatus() {
      if (this.page.status.length === 0) {
        this.page.status = this.processStatuses.map(item => item.value)
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  @import "./styles/processes.scss";
</style>
