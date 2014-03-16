package sketchpad.di.scaldi

import scaldi.Module

class ConfigurationModule extends Module {
  bind [MyService] to new MyServiceImpl
}
