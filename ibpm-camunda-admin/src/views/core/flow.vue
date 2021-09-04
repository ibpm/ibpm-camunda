<template>
  <div ref="container" class="content">
    <div ref="canvas" class="canvas" />
    <div ref="propertiesPanel" class="properties-panel-parent" />
    <ul class="buttons">
      <li>{{ $t('actions.download') }}</li>
      <li>
        <a ref="exportBPMN" :title="$t('core.flow.title.xml')" href="javascript:">{{ $t('core.flow.btn.xml') }}</a>
      </li>
      <li>
        <a ref="exportSvg" :title="$t('core.flow.title.svg')" href="javascript:">{{ $t('core.flow.btn.svg') }}</a>
      </li>
    </ul>
    <div class="custom-area">
      <el-select
        v-model="currentVersion"
        :placeholder="$t('core.process.columns.reversion') + '-' + $t('core.process.columns.version')"
        class="filter-item"
        style="width: 160px"
        @change="changeProcess"
      >
        <el-option v-for="item in processWithVersions" :key="item.version" :label="createVersionLabel(item)" :value="item.version">
          <span style="float: left; font-size: 13px">{{ createVersionLabel(item) }}</span>
          <span style="float: right; color: #8492a6; margin-left: 20px;">{{ item.deployTime | parseTime }}</span>
        </el-option>
      </el-select>
      <span style="margin-left: 40px;">
        <el-popover placement="top" trigger="hover">
          <el-button type="info" @click="save">{{ $t('actions.save') }}</el-button>
          <el-button type="primary" @click="publish">{{ $t('actions.publish') }}</el-button>
          <el-button type="success" @click="saveAndPublish">{{ $t('actions.saveAndPublish') }}</el-button>
          <el-button slot="reference" icon="el-icon-check" type="success" />
        </el-popover>
        <el-tooltip :content="$t('core.flow.tip.exchange')" effect="dark" placement="top">
          <span style="margin-left: 10px;">
            <el-button type="warning" @click="exchange">
              <svg-icon icon-class="exchange" />
            </el-button>
          </span>
        </el-tooltip>
        <el-tooltip :content="$t('core.flow.tip.copy')" effect="dark" placement="top-start">
          <span style="margin-left: 10px;">
            <el-button type="primary" @click="openCopyDialog">
              <svg-icon icon-class="copy" />
            </el-button>
          </span>
        </el-tooltip>
        <el-tooltip :content="$t('actions.start')" effect="dark" placement="top-start">
          <span style="margin-left: 10px;">
            <el-button :disabled="!process.processDefinitionId && process.version === 0" type="danger" @click="start">
              <svg-icon icon-class="hand" />
            </el-button>
          </span>
        </el-tooltip>
      </span>
    </div>

    <!-- dialog -->
    <el-dialog
      :title="editDialog.title"
      :visible.sync="editDialog.copy.visible"
      :center="true"
      :modal="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :before-close="close"
    >
      <div v-show="editDialog.copy.visible">
        <process-info-form :process="targetProcessParam" @save="copy" @cancel="editDialog.copy.visible = false" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getContentReq, updateContentReq, versionsReq, copyReq, exchangeReq, publishReq, startReq } from '@/api/core/process'
import BpmnModeler from 'bpmn-js/lib/Modeler'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
import miniMapModule from 'diagram-js-minimap'
import customTranslate from '@/utils/customTranslate'
import ProcessInfoForm from './components/process/editForm'
import { getTimeStr } from '@/utils/tools'
import customElementTemplate from './components/process/element-templates/custom'
import customControlsModule from './components/process/custom'

export default {
  name: 'flow',
  components: {
    ProcessInfoForm
  },
  data() {
    return {
      process: {},
      jsonData: null,
      currentVersion: -1,
      bpmnModeler: null,
      processWithVersions: [],
      editDialog: {
        title: undefined,
        copy: {
          visible: false
        }
      },
      targetProcessParam: {}
    }
  },
  created() {
    setTimeout(() => this.bindBpmn(), 20)
    this.initProcessWithVersions()
  },
  methods: {
    bindBpmn() {
      const canvas = this.$refs.canvas
      const customTranslateModule = {
        translate: ['value', customTranslate]
      }
      this.bpmnModeler = new BpmnModeler({
        container: canvas,
        propertiesPanel: {
          parent: this.$refs.propertiesPanel
        },
        additionalModules: [
          propertiesProviderModule,
          propertiesPanelModule,
          miniMapModule,
          customTranslateModule,
          customControlsModule
        ],
        elementTemplates: customElementTemplate, // camunda:poperty must be String, Hidden or Dropdown
        moddleExtensions: {
          camunda: camundaModdleDescriptor
        },
        keyboard: {
          bindTo: document
        }
      })
      const self = this
      const downloadLink = this.$refs.exportBPMN
      const downloadSvgLink = this.$refs.exportSvg
      this.bpmnModeler.on('commandStack.changed', function() {
        self.exportSvg(function(svg) {
          self.setEncoded(downloadSvgLink, self.getExportFileName() + '.svg', svg)
        })

        self.exportBPMN(function(xml) {
          self.setEncoded(downloadLink, self.getExportFileName() + '.bpmn.xml', xml)
        })

        self.bindProcessContent(function(xml) {
          self.process.content = xml
        })
      })
      this.initWidget()
    },
    async openDiagram(xml) {
      try {
        const result = await this.bpmnModeler.importXML(xml)
        const { warnings } = result
        this.bpmnModeler.get('canvas').zoom('fit-viewport')
        if (warnings.length > 0) {
          this.$message.warning(warnings)
        }
      } catch (err) {
        this.$message.error(err.message + err.warnings)
      }
    },
    save() {
      this.$confirm(
        this.$t('tip.saveConfirm'),
        this.$t('tip.confirm'),
        { type: 'info' })
        .then(() => {
          updateContentReq(this.process).then(res => {
            this.$message.success(res.data.msg)
            this.openNewProcessPage(this.process.processDefinitionKey)
          })
        })
    },
    publish() {
      this.$confirm(
        this.$t('tip.publishConfirm'),
        this.$t('tip.confirm'),
        { type: 'info' })
        .then(() => {
          publishReq({ processDefinitionKeys: [this.process.processDefinitionKey] }).then(res => {
            this.$message.success(res.data.msg)
            this.openNewProcessPage(this.process.processDefinitionKey)
          })
        })
    },
    saveAndPublish() {
      this.$confirm(
        this.$t('tip.saveAndPublishConfirm'),
        this.$t('tip.confirm'),
        { type: 'info' })
        .then(() => {
          updateContentReq(this.process).then(res => {
            publishReq({ processDefinitionKeys: [this.process.processDefinitionKey] }).then(res => {
              this.$message.success(res.data.msg)
              this.openNewProcessPage(this.process.processDefinitionKey)
            })
          })
        })
    },
    changeProcess() {
      const loading = this.getLoading()
      this.renderContent()
      this.closeLoading(loading)
    },
    initProcessWithVersions() {
      versionsReq({ processDefinitionKey: this.$route.params.key }).then(res => {
        this.processWithVersions = res.data.result.reverse()
        this.currentVersion = this.processWithVersions[0].version
        this.renderContent()
      })
    },
    renderContent() {
      this.process = Object.assign({}, this.processWithVersions.find(item => item.version === this.currentVersion))
      getContentReq(this.process).then(res => {
        this.process.content = res.data.result
        this.openDiagram(this.process.content) // open the process content
      })
    },
    openCopyDialog() {
      this.editDialog.title = this.$t('core.flow.tip.copy')
      this.editDialog.copy.visible = true
      this.targetProcessParam = Object.assign({}, this.process)
    },
    // copy process with current version to another process(if the target was existent, will add its version)
    copy() {
      if (this.process.processDefinitionKey === this.targetProcessParam.processDefinitionKey) {
        this.$message.warning(this.$t('core.flow.tip.processDefinitionKeyNotChanged'))
        return
      }
      this.$confirm(this.$t('core.flow.tip.copyConfirm'), this.$t('tip.confirm'), { type: 'info' })
        .then(() => {
          const data = {
            processDefinitionKey: this.process.processDefinitionKey,
            processDefinitionId: this.process.processDefinitionId,
            targetProcessParam: this.targetProcessParam
          }
          copyReq(data).then(res => {
            this.$message.success(res.data.msg)
            this.editDialog.copy.visible = false
            this.openNewProcessPage(this.targetProcessParam.processDefinitionKey)
          })
        })
    },
    // exchange current version to the latest
    exchange() {
      if (this.currentVersion === this.processWithVersions[0].version) {
        this.$message.warning(this.$t('core.flow.tip.versionIsLatest'))
        return
      }
      this.$confirm(this.$t('core.flow.tip.exchangeConfirm'), this.$t('tip.confirm'), { type: 'info' })
        .then(() => {
          exchangeReq(this.process).then(res => {
            this.$message.success(res.data.msg)
            this.openNewProcessPage(this.process.processDefinitionKey)
          })
        })
    },
    start() {
      if (!this.process.version) {
        this.$message.warning(this.$t('tip.manualWithNoVersion'))
        return
      } else if (this.process.status === 1) {
        this.$message.warning(this.$t('tip.disabledProcessError'))
        return
      }
      this.$confirm(this.$t('tip.confirm'), this.$t('tip.confirmMsg'), { type: 'warning' })
        .then(() => {
          startReq({
            processDefinitionKey: this.process.processDefinitionKey,
            jsonData: (!this.jsonData ? null : (typeof this.jsonData === 'string' ? JSON.parse(this.jsonData) : this.jsonData))
          }).then(res => {
            this.$message.success(res.data.msg)
            setTimeout(() => {
              this.$router.push({ name: 'total', replace: true })
            }, 600)
          })
        })
    },
    // open new route
    openNewProcessPage(key) {
      const loading = this.getLoading()
      setTimeout(() => {
        const route = {
          name: 'flow',
          params: {
            key: key
          }
        }
        this.$router.push(route)
      }, 100)
      this.initProcessWithVersions()
      this.closeLoading(loading)
    },
    createVersionLabel(item) {
      let label = item.version
      if (item.processDefinitionId) {
        label += ' - ' + item.version
      }
      return label
    },
    async exportSvg(done) {
      try {
        const result = await this.bpmnModeler.saveSVG()
        const { svg } = result
        done(svg)
      } catch (err) {
        this.$message.error(err)
      }
    },
    async exportBPMN(done) {
      try {
        const result = await this.bpmnModeler.saveXML({ format: true })
        const { xml } = result
        done(xml)
      } catch (err) {
        this.$message.error(err)
      }
    },
    setEncoded(link, name, data) {
      const encodedData = encodeURIComponent(data)
      if (data) {
        link.className = 'active'
        link.href = 'data:application/bpmn20-xml;charset=UTF-8,' + encodedData
        link.download = name
      }
    },
    getExportFileName() {
      return (this.process.processDefinitionName || this.process.processDefinitionKey || 'undefined') + '-' + getTimeStr()
    },
    bindProcessContent(done) {
      this.exportBPMN(done)
    },
    initWidget() {
      const self = this
      function registerFileDrop(container, callback) {
        function handleFileSelect(e) {
          e.stopPropagation()
          e.preventDefault()
          var files = e.dataTransfer.files
          var file = files[0]
          var reader = new FileReader()
          reader.onload = function(e) {
            var xml = e.target.result
            callback(xml)
          }
          reader.readAsText(file)
        }
        function handleDragOver(e) {
          e.stopPropagation()
          e.preventDefault()
          e.dataTransfer.dropEffect = 'copy' // Explicitly show this is a copy.
        }
        container.ondragover = handleDragOver
        container.ondrop = handleFileSelect
      }
      // file drag / drop ///////////////////////
      // check file api availability
      if (!window.FileList || !window.FileReader) {
        window.alert(
          'Looks like you use an older browser that does not support drag and drop. ' +
            'Try using Chrome, Firefox or the Internet Explorer > 10.')
      } else {
        registerFileDrop(self.$refs.container, this.openDiagram)
      }
    },
    getLoading() {
      return this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
    },
    closeLoading(loading) {
      setTimeout(() => {
        loading.close()
      }, 200)
    },
    close() {
      this.editDialog.copy.visible = false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  @import "~@/styles/bpmn.scss";
</style>
