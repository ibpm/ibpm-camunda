<template>
  <span slot="reference" style="font-size: 17px;">{{ currentTimeStr }}</span>
</template>

<script>
import { currentTimeReq } from '@/api/core/tool'
import { parseTime } from '@/utils'

export default {
  name: 'ExecutionPlan',
  data() {
    return {
      timer: null,
      now: null,
      currentTimeStr: null
    }
  },
  created() {
    currentTimeReq().then(res => {
      this.now = new Date(res.data.result)
      this.timer = setInterval(() => {
        this.now = new Date(this.now.getTime() + 1000)
        this.currentTimeStr = parseTime(this.now)
      }, 1000)
    })
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>
