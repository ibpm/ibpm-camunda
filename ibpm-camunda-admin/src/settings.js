module.exports = {
  title: 'title',

  /**
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  showSettings: true,

  /**
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  tagsView: true,

  /**
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: false,

  /**
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: false,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production',

  /**
   * Date format
   */
  dateFormatPattern: 'yyyy-MM-dd HH:mm:ss',

  /**
   * API path
   */
  APIPath: {
    SYS: 'sys',
    CORE: 'core',
    STATISTICS: 'statistics',
    LOGIN: '/login',
    USER: '/user',
    ROLE: '/role',
    RESOURCE: '/resource',
    JOB: '/process',
    BPM: '/bpm',
    ARG: '/arg',
    CALENDAR: '/calendar',
    INSTANCE: '/instance',
    COUNT: '/count',
    TOOL: '/tool',
    GET: '/get'
  },
  /**
   * app config
   */
  alias: {
    comma: ',,',
    colon: '::'
  }
}
