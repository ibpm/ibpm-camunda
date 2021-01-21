<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="page.processDefinitionKey" :placeholder="$t('core.process.columns.processDefinitionKey')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-input v-model="page.processDefinitionName" :placeholder="$t('core.process.columns.processDefinitionName')" class="filter-item search-input" @keyup.enter.native="search" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="search" />
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
      @sort-change="sortChange"
    >
      <el-table-column :label="$t('columns.title')" show-overflow-tooltip width="200px">
        <template slot-scope="scope">
          <el-link type="primary" @click="openForm(scope.row)">{{ scope.row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column :label="$t('core.process.columns.processDefinitionKey')" prop="processDefinitionKey" min-width="100px">
        <template slot-scope="scope">
          <span>{{ scope.row.processDefinitionKey }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('core.process.columns.processDefinitionName')" min-width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.processDefinitionName }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('columns.startTime')" min-width="100px">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime | parseTime }}</span>
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
  </div>
</template>

<script>
import { listTodoReq } from '@/api/instance/instance'
import { startReq } from '@/api/core/process'
import * as calendarApi from '@/api/core/calendar'
import { buildMsg } from '@/utils/tools'
import Pagination from '@/components/Pagination'

const DEF_OBJ = {
  processDefinitionKey: undefined,
  processDefinitionName: '',
  status: 0,
  remark: '',
  content: ''
}
export default {
  name: 'start',
  components: {
    Pagination
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
        status: 0,
        sort: 'START_TIME DESC'
      },
      processStatuses: [],
      milliSecondTimeUnits: [],
      inDayTimeUnits: [],
      dayTimeUnit: [],
      overDayTimeUnits: [],
      daysOfWeek: [],
      showCreateTime: false,
      process: DEF_OBJ,
      downloadLoading: false,
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
    openForm(row) {
      const processDefinitionKeys = [row.processDefinitionKey]
      this.$confirm(buildMsg(this, processDefinitionKeys), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          startReq({ processDefinitionKeys: processDefinitionKeys }).then(res => {
            if (res.data.result) {
              this.$router.push({
                name: res.data.result,
                params: {
                  ...row,
                  isDraft: true
                }
              })
            } else {
              this.$message.success(res.data.msg)
            }
          })
        })
    },
    search() {
      this.listLoading = true
      listTodoReq(this.page).then(response => {
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
        this.page.sort = 'START_TIME'
      } else {
        this.page.sort = 'START_TIME DESC'
      }
      this.search()
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
    initCalendars() {
      calendarApi.listReq().then(res => {
        this.calendars = res.data.result
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  @import "./styles/processes.scss";
</style>
