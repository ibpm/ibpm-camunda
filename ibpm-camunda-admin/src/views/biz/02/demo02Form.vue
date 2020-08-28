<template>
  <div class="app-container">
    <el-row>
      <el-col :offset="1" :span="8">
        <el-form ref="form" :model="bizData" :rules="rules" :disabled="!bizData.isDraft && bizData.procInstId" label-width="80px">
          <el-form-item :label="$t('columns.title')" prop="title">
            <el-input v-model="bizData.title" />
          </el-form-item>
          <el-form-item :label="$t('biz.startDate')" prop="startDate">
            <el-date-picker
              v-model="bizData.startDate"
              type="date"
              value-format="timestamp"
            />
          </el-form-item>
          <el-form-item :label="$t('biz.dayNum')" prop="num">
            <el-input-number v-model="bizData.num" :min="1" :step="1" />
          </el-form-item>
          <el-form-item :label="$t('biz.reason')" prop="reason">
            <el-input v-model="bizData.reason" type="textarea" :rows="2" />
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row>
      <el-col :offset="1" :span="8">
        <Operation :biz-data="bizData" />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Operation from '@/views/core/components/process/operation'

export default {
  components: {
    Operation
  },
  data() {
    return {
      bizData: {
        title: '',
        startDate: new Date(),
        num: 1,
        destination: '',
        tool: '',
        reason: ''
      },
      rules: {
        title: [
          { required: true, trigger: 'blur' }
        ],
        startDate: [
          { type: 'date', required: true, trigger: 'change' }
        ],
        num: [
          { type: 'number', required: true, trigger: 'change' }
        ],
        reason: [
          { required: true, trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
