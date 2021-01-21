<template>
  <div class="app-container">
    <el-row>
      <el-col :offset="1" :span="8">
        <el-form ref="form" :model="bizData" :rules="rules" :disabled="!bizData.isDraft && bizData.processInstanceId" label-width="80px">
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
          <el-form-item :label="$t('biz.destination')" prop="destination">
            <el-select v-model="bizData.destination">
              <el-option key="1" :label="$t('biz.bei_jing')" :value="$t('biz.bei_jing')" />
              <el-option key="2" :label="$t('biz.shang_hai')" :value="$t('biz.shang_hai')" />
              <el-option key="3" :label="$t('biz.guang_zhou')" :value="$t('biz.guang_zhou')" />
              <el-option key="4" :label="$t('biz.shen_zhen')" :value="$t('biz.shen_zhen')" />
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('biz.trafficTool')" prop="tool">
            <el-radio-group v-model="bizData.tool">
              <el-radio-button :label="$t('biz.air_plane')" />
              <el-radio-button :label="$t('biz.high_speed_railway')" />
            </el-radio-group>
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
        destination: [
          { required: true, trigger: 'change' }
        ],
        tool: [
          { required: true, trigger: 'change' }
        ],
        reason: [
          { required: true, trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
