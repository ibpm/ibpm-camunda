<template>
  <div>
    <el-button type="info" @click="goBack">返回</el-button>
    <el-button type="primary" @click="seeDiagram">流程图</el-button>
    <el-button v-if="isDraft || !bizData.processInstanceId" type="primary" @click="save">保存</el-button>
    <el-button v-if="isDraft || !bizData.processInstanceId" type="success" @click="create">发起</el-button>
    <el-button v-if="!isDraft && bizData.taskId" type="success" @click="approve">审批</el-button>
  </div>
</template>

<script>
import { getReq, saveReq, createReq, approveReq } from '@/api/core/bpm'

export default {
  name: 'operation',
  props: {
    bizData: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      isDraft: false
    }
  },
  created() {
    this.isDraft = this.$route.params.isDraft || false
    if (this.bizData) {
      Object.assign(this.bizData, this.$route.params)
    }
    if (this.$route.params.businessKey) {
      this.get()
    }
  },
  methods: {
    // close and go back
    goBack() {
      const view = this.$route
      this.$router.go(-1)
      this.$store.dispatch('tagsView/delView', view)
    },
    // see bpmn or instance diagram
    seeDiagram() {},
    // save draft
    save() {
      saveReq(this.bizData).then(res => {
        this.$message.success(res.data.msg)
      })
    },
    // create process
    create() {
      createReq(this.bizData).then(res => {
        this.$message.success(res.data.msg)
      })
    },
    // approve task
    approve() {
      approveReq(this.bizData).then(res => {
        this.$message.success(res.data.msg)
      })
    },
    get() {
      getReq({
        businessKey: this.$route.params.businessKey
      }).then(res => {
        Object.assign(this.bizData, res.data.result)
      })
    }
  }
}
</script>
