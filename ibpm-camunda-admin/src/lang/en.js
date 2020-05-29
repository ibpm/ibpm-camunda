export default {
  title: 'Ibpm-camunda',
  route: {
    dashboard: 'Dashboard',
    sys: 'System',
    user: 'User',
    role: 'Role',
    calendar: 'Calendar',
    core: 'Core',
    job: 'Job',
    arg: 'Arg',
    monitor: 'Monitor',
    total: 'Execution',
    retry: 'Retry',
    scheduler: 'Scheduler',
    executor: 'Executor',
    application: 'Application'
  },
  actions: {
    handle: 'Handle',
    add: 'Add',
    update: 'Update',
    save: 'Save',
    search: 'Search',
    refresh: 'Refresh',
    remove: 'Remove',
    cancel: 'Cancel',
    reset: 'Reset',
    transferIn: 'Transfer in',
    transferOut: 'Transfer out',
    import: 'Import',
    importModel: 'Import model',
    exportModel: 'Export model',
    exportList: 'Export list',
    last: 'Last',
    next: 'Next',
    ok: 'OK',
    download: 'Download',
    use: 'Use',
    manual: 'Manual',
    showCurrent: 'Show current',
    showAll: 'Show all',
    highOperation: 'High operation',
    highSearch: 'High search',
    exclude: 'Exclude',
    connectTest: 'Test connection',
    saveDate: 'Save',
    testSql: 'Test sql',
    retry: 'Retry',
    terminate: 'Terminate',
    publish: 'Publish',
    saveAndPublish: 'Save&Publish',
    enable: 'Enable',
    disable: 'Disable',
    upload: 'Upload',
    load: 'Load',
    unload: 'Unload',
    test: 'Test',
    changePassword: 'Password'
  },
  tip: {
    usernameError: 'Please enter the correct userName',
    passwordError: 'Please enter the correct password',
    search: 'Please enter search key',
    confirm: 'Please confirm',
    confirmMsg: 'Make sure',
    selectData: 'Please select data',
    saveConfirm: 'Make sure to save',
    publishConfirm: 'Make sure to publish',
    saveAndPublishConfirm: 'Make sure to save and publish',
    disabledJobError: 'Can not start the disabled job',
    adminUserCannotBeRemoved: 'Can not delete super user',
    preview: 'Preview',
    selectSql: 'Must start with select',
    howToPreview: 'Double click to preview',
    sqlResultAlertInfo: 'Total{}(Tip:Only preview top 10)',
    sqlResultNoDataInfo: 'Execute success but no data return',
    previewing: 'Previewing......',
    formatError: 'Format error',
    pingError: 'Can not ping successfully',
    yes: 'Yes',
    no: 'No',
    flowNotStart: 'Job was not started successfully, can not retry or view detail',
    fileSizeOutOfBound: 'File size can not more than ',
    currentFileSize: 'The file size is ',
    entryVersion: 'Please entry version',
    versionNotBlank: 'The version can not be blank',
    beforeActivity: 'Start before the activity',
    afterActivity: 'Start after the activity',
    refreshPage: 'Please wait for refreshing page',
    noneScheduler: 'None scheduler',
    noneExecutor: 'None executor',
    detail: 'Detail',
    fileSeparatorLeft: 'Please using / as file separator, such as /data/biz',
    ftpPrefix: 'Such as if you want to use mainFtpIp..., entry main here(for default is ftpIp...)',
    dbNameInArg: 'Entry the datasource name which is in datasource',
    testResult: 'Test Result',
    noLongerTriggered: 'The trigger can not be triggered in the future',
    userFrozen: 'User is frozen, login will be denied',
    userDisabled: 'User is disabled, job will skip executing',
    oldPassword: 'Old password',
    newPassword: 'New Password',
    doubleNewPassword: 'Double check',
    differentOldPassword: 'The old and new password must be different',
    differentNewPassword: 'The new password is different',
    jobNoInstance: 'No instance based on the query param',
    noResources: 'Menus can\'t be accessible,please contact super admin',
    today: 'Today',
    yesterday: 'Yesterday',
    lastWeek: 'Last Week',
    lastMonth: 'Last Month',
    lastYear: 'Last Year'
  },
  columns: {
    userName: 'User name',
    password: 'Password',
    ip: 'IP',
    displayName: 'Display name',
    status: 'Status',
    remark: 'Remark',
    createTime: 'Create time',
    updateTime: 'Update time',
    startTime: 'Start time',
    endTime: 'End time',
    repeatInterval: 'Repeat Interval',
    prefix: 'Prefix'
  },
  rules: {
    userName: 'The user name can not be blank',
    password: 'The password can not be blank',
    displayName: 'Display name can not be blank'
  },
  placeholders: {
    userName: 'Please entry user name',
    password: 'Please entry password',
    displayName: 'Please entry display name',
    remark: 'Please entry remark'
  },
  sys: {
    user: {
      columns: {
        email: 'Email',
        mobile: 'Mobile',
        sendConfig: 'Alarm config'
      },
      placeholder: {
        email: 'Please entry email',
        mobile: 'Please entry mobile'
      },
      rules: {
        oldPassword: 'The old password can not be blank',
        newPassword: 'The new password can not be blank',
        doubleNewPassword: 'The double new password can not be blank'
      }
    },
    role: {
      columns: {
        roleName: 'Role name'
      },
      rules: {
        roleName: 'The role name can not be blank'
      },
      placeholder: {
        roleName: 'Please entry role name'
      },
      tip: {
        user: 'Allocate user',
        resource: 'Allocate resource',
        userFilterTip: 'User user name or display name',
        root: 'Root'
      },
      actions: {
        user: 'User',
        resource: 'Resource'
      },
      label: {
        allocateUser: 'Allocate user',
        allocateResource: 'Allocate resource'
      }
    }
  },
  core: {
    title: 'Core',
    job: {
      rules: {
        jobName: 'The job name can not be blank'
      },
      columns: {
        reversion: 'Reversion',
        version: 'Version',
        jobName: 'Job name'
      },
      placeholder: {
        jobName: 'Please entry job name'
      },
      actions: {
        design: 'Design',
        param: 'Param'
      },
      exportConfig: {
        full: 'Full',
        increment: 'Increment'
      }
    },
    flow: {
      btn: {
        xml: 'XML',
        svg: 'SVG'
      },
      title: {
        xml: 'download as .xml(.bpmn) file',
        svg: 'download as .svg image'
      },
      tip: {
        copy: 'Copy',
        exchange: 'Exchange to latest using current version',
        copyConfirm: 'Confirm to copy',
        exchangeConfirm: 'Confirm to exchange',
        jobNameNotChanged: 'Must rename the job name when you copy it',
        versionIsLatest: 'The version is latest, need not to exchange'
      }
    },
    arg: {
      columns: {
        argName: 'Arg name',
        argType: 'Arg type',
        argValue: 'Arg value'
      },
      placeholder: {
        argName: 'Please entry arg name',
        argValue: 'Please entry arg value',
        genericType: 'Generic Type',
        mapKey: 'Please entry map key',
        mapValue: 'Please entry map value',
        calendarName: 'Please select calendar'
      },
      rules: {
        argName: 'The arg name can not be blank',
        argValue: 'The arg value can not be blank'
      }
    }
  },
  monitor: {
    columns: {
      duration: 'duration',
      msg: 'msg',
      schedulerUri: 'scheduler uri',
      executorUri: 'executor uri'
    },
    label: {
      job: 'Job',
      record: 'Record',
      task: 'Task'
    }
  },
  serverInfo: {
    base: {
      memory: 'Memory',
      machine: 'Machine',
      service: 'Service',
      resource: 'Resource',
      file: 'File'
    },
    service: {
      vipAddress: 'Vip address',
      homePageUrl: 'Home page url',
      status: 'Status',
      lastDirtyTimestamp: 'Last dirty timestamp',
      lastUpdatedTimestamp: 'Last updated timestamp'
    },
    cpu: {
      coreNum: 'Core num',
      appRate: 'App rate',
      systemRate: 'System rate',
      usedRate: 'Used rate',
      idleRate: 'Idle rate',
      ioWaitRate: 'Io wait rate'
    },
    memory: {
      totalSize: 'Total size',
      used: 'Used',
      available: 'Available'
    },
    machine: {
      system: 'Operating system',
      osName: 'Os name',
      osArch: 'Os architecture',
      osVersion: 'Os version'
    },
    jvm: {
      vmName: 'Jvm name',
      version: 'Java version',
      homePath: 'Home path',
      startTime: 'Start time',
      maxMemorySize: 'Max memory size',
      totalMemorySize: 'Total memory size',
      usedMemory: 'Used memory',
      freeMemory: 'Free memory'
    },
    resource: {
      pid: 'PID',
      userName: 'User name',
      userHome: 'User home',
      userDir: 'User directory',
      tempDir: 'Temp directory'
    }
  },
  statistics: {
    chart: 'Chart',
    instance: 'Instance',
    job: 'Job',
    user: 'User'
  },
  chart: {
    instance: 'Instance status chart',
    job: 'Job status chart',
    user: 'User status chart',
    instanceDuration: 'Instance duration chart'
  },
  navbar: {
    logOut: 'Log Out',
    docs: 'Document',
    theme: 'Theme',
    size: 'Global Size'
  },
  login: {
    title: 'Login Form',
    logIn: 'Log in'
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close others',
    closeAll: 'Close all'
  },
  settings: {
    title: 'Page style setting',
    theme: 'Theme color',
    tagsView: 'Open tags view',
    sidebarLogo: 'Sidebar logo'
  }
}
