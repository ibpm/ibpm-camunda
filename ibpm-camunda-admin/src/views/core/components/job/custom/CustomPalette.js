export default class CustomPalette {
  constructor(create, elementFactory, palette, translate) {
    this.create = create
    this.elementFactory = elementFactory
    this.translate = translate

    palette.registerProvider(this)
  }

  getPaletteEntries(element) {
    const {
      create,
      elementFactory,
      translate
    } = this

    function createShape(event, key) {
      const shape = elementFactory.createShape({ type: 'bpmn:' + key })

      create.start(event, shape)
    }

    /**
    function createParallelGateway(event) {
      createShape(event, 'ParallelGateway')
    }
    */

    function createUserTask(event) {
      createShape(event, 'UserTask')
    }

    function createServiceTask(event) {
      createShape(event, 'ServiceTask')
    }

    function createScriptTask(event) {
      createShape(event, 'ScriptTask')
    }

    function createCallActivity(event) {
      createShape(event, 'CallActivity')
    }

    return {
      /**
      'append.parallel-gateway': {
        group: 'activity',
        className: 'bpmn-icon-gateway-parallel',
        title: translate('Create ParallelGateway'),
        action: {
          dragstart: createParallelGateway,
          click: createParallelGateway
        }
      },
       */
      'create.user-task': {
        group: 'activity',
        className: 'bpmn-icon-user-task',
        title: translate('Create UserTask'),
        action: {
          dragstart: createUserTask,
          click: createUserTask
        }
      },
      'create.service-task': {
        group: 'activity',
        className: 'bpmn-icon-service-task',
        title: translate('Create ServiceTask'),
        action: {
          dragstart: createServiceTask,
          click: createServiceTask
        }
      },
      'create.script-task': {
        group: 'activity',
        className: 'bpmn-icon-script-task',
        title: translate('Create ScriptTask'),
        action: {
          dragstart: createScriptTask,
          click: createScriptTask
        }
      },
      'create.call-activity': {
        group: 'activity',
        className: 'bpmn-icon-call-activity',
        title: translate('Create CallActivity'),
        action: {
          dragstart: createCallActivity,
          click: createCallActivity
        }
      }
    }
  }
}

CustomPalette.$inject = [
  'create',
  'elementFactory',
  'palette',
  'translate'
]
