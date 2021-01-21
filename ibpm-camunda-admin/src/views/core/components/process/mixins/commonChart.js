import DateTimeGenerator from '@/components/DateTimeGenerator'

export default {
  components: { DateTimeGenerator },
  props: {
    processDefinitionKey: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      visible: true,
      firedSources: []
    }
  },
  created() {
    this.loadConstant()
  },
  methods: {
    loadConstant() {
      const lang = localStorage.getItem('language')
      import('@/lang/dict.js').then(array => {
        this.firedSources = array['firedSources_' + lang]
      })
    }
  }
}
