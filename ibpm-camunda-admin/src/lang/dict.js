// zh
export const userStatuses_zh = [
  {
    value: 0,
    label: '正常'
  },
  {
    value: 1,
    label: '冻结'
  },
  {
    value: 2,
    label: '禁用'
  }
]

export const jobStatuses_zh = [
  {
    label: '启用',
    value: 0
  },
  {
    label: '禁用',
    value: 1
  }
]

export const milliSecondTimeUnits_zh = [
  {
    label: '毫秒',
    value: 'MILLISECOND'
  }
]

export const inDayTimeUnits_zh = [
  {
    label: '秒',
    value: 'SECOND'
  },
  {
    label: '分钟',
    value: 'MINUTE'
  },
  {
    label: '小时',
    value: 'HOUR'
  }
]

export const dayTimeUnit_zh = [
  {
    label: '天',
    value: 'DAY'
  }
]

export const overDayTimeUnits_zh = [
  {
    label: '周',
    value: 'WEEK'
  },
  {
    label: '月',
    value: 'MONTH'
  },
  {
    label: '季度',
    value: 'SEASON'
  },
  {
    label: '半年',
    value: 'HALF_YEAR'
  },
  {
    label: '年',
    value: 'YEAR'
  }
]

export const daysOfWeek_zh = [
  {
    label: '星期天',
    value: '1'
  },
  {
    label: '星期一',
    value: '2'
  },
  {
    label: '星期二',
    value: '3'
  },
  {
    label: '星期三',
    value: '4'
  },
  {
    label: '星期四',
    value: '5'
  },
  {
    label: '星期五',
    value: '6'
  },
  {
    label: '星期六',
    value: '7'
  }
]

export const uriTypes_zh = [
  {
    label: '服务发现',
    value: 0
  },
  {
    label: 'IP地址和端口',
    value: 1
  },
  {
    label: '域名',
    value: 2
  }
]

export const draftInstanceStatus_zh = [
  {
    label: '草稿',
    value: 0
  }
]

export const runningInstanceStatus_zh = [
  {
    label: '流转中',
    value: 1
  }
]

export const rejectedInstanceStatus_zh = [
  {
    label: '被驳回',
    value: 2
  }
]

export const doneInstanceStatus_zh = [
  {
    label: '办结',
    value: -1
  }
]

export const terminatedInstanceStatus_zh = [
  {
    label: '终止',
    value: -2
  }
]

export const removedInstanceStatus_zh = [
  {
    label: '删除',
    value: -3
  }
]

export const doingInstanceStatuses_zh = [
  ...draftInstanceStatus_zh,
  ...runningInstanceStatus_zh,
  ...rejectedInstanceStatus_zh
]

export const doneInstanceStatuses_zh = [
  ...doneInstanceStatus_zh,
  ...terminatedInstanceStatus_zh,
  ...removedInstanceStatus_zh
]

export const instanceStatuses_zh = [
  ...draftInstanceStatus_zh,
  ...runningInstanceStatus_zh,
  ...rejectedInstanceStatus_zh,
  ...doneInstanceStatus_zh,
  ...terminatedInstanceStatus_zh,
  ...removedInstanceStatus_zh
]

export const requestMethods_zh = [
  {
    label: 'POST',
    value: 'POST'
  },
  {
    label: 'GET',
    value: 'GET'
  },
  {
    label: 'PUT',
    value: 'PUT'
  },
  {
    label: 'DELETE',
    value: 'DELETE'
  }
]

export const transferTitles_zh = [
  '移出',
  '移入'
]

export const allocateTitles_zh = [
  '待分配',
  '已分配'
]

export const baseMisfireInstructions_zh = [
  {
    label: '忽略',
    value: -1
  },
  {
    label: '灵活',
    value: 0
  },
  {
    label: '立即触发',
    value: 1
  }
]

export const misfireInstructions_zh = [
  ...baseMisfireInstructions_zh,
  {
    label: '不触发',
    value: 2
  }
]

export const simpleTriggerMisfireInstructions_zh = [
  ...baseMisfireInstructions_zh,
  {
    label: '立即触发(以当前重复次数)',
    value: 2
  },
  {
    label: '立即触发(以剩余重复次数)',
    value: 3
  },
  {
    label: '下次触发(以剩余重复次数)',
    value: 4
  },
  {
    label: '下次触发(以当前重复次数)',
    value: 5
  }
]

export const jobCharts_zh = [
  {
    label: '执行时长',
    value: 'durationChart'
  },
  {
    label: '执行状态',
    value: 'statusChart'
  }
]

export const durationOrders_zh = [
  {
    label: '按开始时间升序',
    value: 'START_TIME'
  },
  {
    label: '按开始时间降序',
    value: 'START_TIME DESC'
  },
  {
    label: '按执行时长升序',
    value: 'DURATION'
  },
  {
    label: '按执行时长降序',
    value: 'DURATION DESC'
  }
]

export const firedSources_zh = [
  {
    label: '触发器触发',
    value: 'IS NOT NULL'
  },
  {
    label: '手动执行',
    value: 'IS NULL'
  },
  {
    label: '全部',
    value: ''
  }
]

export const sendConfigs_zh = [
  '邮件',
  '钉钉',
  '企业微信'
]

// en
export const userStatuses_en = [
  {
    value: 0,
    label: 'Normal'
  },
  {
    value: 1,
    label: 'Frozen'
  },
  {
    value: 2,
    label: 'Disabled'
  }
]

export const jobStatuses_en = [
  {
    label: 'Enable',
    value: 0
  },
  {
    label: 'Disable',
    value: 1
  }
]

export const milliSecondTimeUnits_en = [
  {
    label: 'MilliSecond',
    value: 'MILLISECOND'
  }
]

export const inDayTimeUnits_en = [
  {
    label: 'Second',
    value: 'SECOND'
  },
  {
    label: 'Minute',
    value: 'MINUTE'
  },
  {
    label: 'Hour',
    value: 'HOUR'
  }
]

export const dayTimeUnit_en = [
  {
    label: 'Day',
    value: 'DAY'
  }
]

export const overDayTimeUnits_en = [
  {
    label: 'Week',
    value: 'WEEK'
  },
  {
    label: 'Month',
    value: 'MONTH'
  },
  {
    label: 'Season',
    value: 'SEASON'
  },
  {
    label: 'Half-Year',
    value: 'HALF_YEAR'
  },
  {
    label: 'Year',
    value: 'YEAR'
  }
]

export const daysOfWeek_en = [
  {
    label: 'Sunday',
    value: '1'
  },
  {
    label: 'Monday',
    value: '2'
  },
  {
    label: 'Tuesday',
    value: '3'
  },
  {
    label: 'Wednesday',
    value: '4'
  },
  {
    label: 'Thursday',
    value: '5'
  },
  {
    label: 'Friday',
    value: '6'
  },
  {
    label: 'Saturday',
    value: '7'
  }
]

export const uriTypes_en = [
  {
    label: 'Discovery Client',
    value: '0'
  },
  {
    label: 'ip:port',
    value: '1'
  },
  {
    label: 'Domain Name',
    value: '2'
  }
]

export const draftInstanceStatus_en = [
  {
    label: 'Draft',
    value: 0
  }
]

export const runningInstanceStatus_en = [
  {
    label: 'Running',
    value: 1
  }
]

export const rejectedInstanceStatus_en = [
  {
    label: 'Rejected',
    value: 2
  }
]

export const doneInstanceStatus_en = [
  {
    label: 'Done',
    value: -1
  }
]

export const terminatedInstanceStatus_en = [
  {
    label: 'Terminated',
    value: -2
  }
]

export const removedInstanceStatus_en = [
  {
    label: 'Removed',
    value: -3
  }
]

export const doingInstanceStatuses_en = [
  ...draftInstanceStatus_en,
  ...runningInstanceStatus_en,
  ...rejectedInstanceStatus_en
]

export const doneInstanceStatuses_en = [
  ...doneInstanceStatus_en,
  ...terminatedInstanceStatus_en,
  ...removedInstanceStatus_en
]

export const instanceStatuses_en = [
  ...draftInstanceStatus_en,
  ...runningInstanceStatus_en,
  ...rejectedInstanceStatus_en,
  ...doneInstanceStatus_en,
  ...terminatedInstanceStatus_en,
  ...removedInstanceStatus_en
]

export const requestMethods_en = [
  {
    label: 'POST',
    value: 'POST'
  },
  {
    label: 'GET',
    value: 'GET'
  },
  {
    label: 'PUT',
    value: 'PUT'
  },
  {
    label: 'DELETE',
    value: 'DELETE'
  }
]

export const transferTitles_en = [
  'pull',
  'push'
]

export const allocateTitles_en = [
  'exclude',
  'included'
]

export const baseMisfireInstructions_en = [
  {
    label: 'Ignore misfire policy',
    value: -1
  },
  {
    label: 'Smart policy',
    value: 0
  },
  {
    label: 'Fire once now',
    value: 1
  }
]

export const misfireInstructions_en = [
  ...baseMisfireInstructions_en,
  {
    label: 'Do nothing',
    value: 2
  }
]

export const simpleTriggerMisfireInstructions_en = [
  ...baseMisfireInstructions_en,
  {
    label: 'Reschedule now with existing repeat count',
    value: 2
  },
  {
    label: 'Reschedule now with remaining repeat count',
    value: 3
  },
  {
    label: 'Reschedule next with remaining count',
    value: 4
  },
  {
    label: 'Reschedule next with existing count',
    value: 5
  }
]

export const jobCharts_en = [
  {
    label: 'Duration',
    value: 'durationChart'
  },
  {
    label: 'Status',
    value: 'statusChart'
  }
]

export const durationOrders_en = [
  {
    label: 'start time asc',
    value: 'START_TIME'
  },
  {
    label: 'start time desc',
    value: 'START_TIME DESC'
  },
  {
    label: 'duration asc',
    value: 'DURATION'
  },
  {
    label: 'duration desc',
    value: 'DURATION DESC'
  }
]

export const firedSources_en = [
  {
    label: 'Trigger fired',
    value: 'IS NOT NULL'
  },
  {
    label: 'Manual start',
    value: 'IS NULL'
  },
  {
    label: 'All',
    value: ''
  }
]

export const sendConfigs_en = [
  'Email',
  'DingTalk',
  'WxWork'
]
