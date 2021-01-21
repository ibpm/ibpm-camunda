<template>
  <el-form
    ref="baseForm"
    :rules="rules"
    :model="process"
    label-position="left"
    label-width="150px"
    class="form-layout"
  >
    <el-form-item :label="$t('core.process.columns.processDefinitionKey')" prop="processDefinitionKey">
      <el-input v-model="process.processDefinitionKey" :placeholder="$t('core.process.placeholder.processDefinitionKey')" />
    </el-form-item>
    <el-form-item :label="$t('core.process.columns.processDefinitionName')" prop="processDefinitionName">
      <el-input v-model="process.processDefinitionName" :placeholder="$t('core.process.placeholder.processDefinitionName')" />
    </el-form-item>
    <el-form-item :label="$t('columns.status')" prop="status">
      <el-select v-model="process.status" class="filter-item" style="width: 100%;">
        <el-option v-for="item in processStatuses" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
    </el-form-item>
    <el-form-item :label="$t('columns.remark')">
      <el-input
        v-model="process.remark"
        :autosize="{ minRows: 1, maxRows: 5}"
        :placeholder="$t('placeholders.remark')"
        type="textarea"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="success" @click="save">{{ $t('actions.save') }}</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

export default {
  name: 'ProcessInfoForm',
  props: {
    process: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      rules: {
        processDefinitionKey: [{ required: true, trigger: 'blur', range: { max: 64 }, pattern: /^[a-zA-Z0-9_-]+$/ }],
        processDefinitionName: [{ required: true, trigger: 'blur', range: { max: 255 }}]
      },
      processStatuses: []
    }
  },
  created() {
    this.loadConst()
    this.setFormRules()
  },
  methods: {
    setFormRules() {
      this.rules.processDefinitionKey[0].message = this.$t('core.process.rules.processDefinitionKey')
      this.rules.processDefinitionName[0].message = this.$t('core.process.rules.processDefinitionName')
    },
    save() {
      this.$refs.baseForm.validate((valid) => {
        if (valid) {
          this.$emit('save', this.process)
        }
      })
    },
    clearValidate() {
      this.$nextTick(() => {
        this.$refs['baseForm'].clearValidate()
      })
    },
    loadConst() {
      const lang = localStorage.getItem('language')
      import('@/lang/dict.js').then(array => {
        this.processStatuses = array['processStatuses_' + lang]
      })
    }
  }
}
</script>
