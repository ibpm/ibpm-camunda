<template>
  <div>
    <el-select v-model="chartValue">
      <el-option v-for="item in processCharts" :key="item.value" :label="item.label" :value="item.value" />
    </el-select>
    <component :is="chartValue" :ref="chartValue" :process-name="processDefinitionKey" style="margin-top: 10px;" />
  </div>
</template>

<script>
import StatusChart from './statusChart'

export default {
  components: {
    StatusChart
  },
  props: {
    processDefinitionKey: {
      type: String,
      default: null
    },
    processCharts: {
      type: Array,
      default: null
    }
  },
  data() {
    return {
      chartValue: null
    }
  },
  methods: {
    reRender() {
      this.$nextTick(() => {
        if (this.$refs[this.chartValue]) {
          this.$refs[this.chartValue].search()
        }
      })
    }
  }
}
</script>
