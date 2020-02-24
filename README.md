# Polybar Integration
Tells your systems [Polybar](https://github.com/polybar/polybar) some information about the current game state in RuneLite

## Screenshots
![Woodcutting](screenshots/Woodcutting.png?raw=true "Woodcutting")
![Idle](screenshots/Idle.png?raw=true "Wasting XP")

## Example Polybar module config
    [module/runelite]
    type = custom/ipc
    hook-0 = test -e /tmp/rl_state && cat /tmp/rl_state
    initial = 1

## How it works
The plugin makes use of [Polybars IPC feature](https://github.com/polybar/polybar/wiki/Module:-ipc). Every implemented game state is written out to the file `/tmp/rl_state`. Using polybar-msg, every instance of Polybar will then be triggered to execute the customizable hook, which should be configured appropiately to read `/tmp/rl_state` (see example config)

## See
https://github.com/polybar/polybar  
https://wiki.archlinux.org/index.php/Polybar  
https://polybar.github.io/  
