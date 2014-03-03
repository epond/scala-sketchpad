package sketchpad.di.subcut

import com.escalatesoft.subcut.inject.NewBindingModule

object ConfigurationModule extends NewBindingModule (module => {
  import module._  // optional but convenient - allows use of bind instead of module.bind

  bind [MyService] toSingle new MyServiceImpl
})
