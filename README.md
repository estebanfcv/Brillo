Brillo
======

Programa que se encarga de configurar el brillo de la pantalla en Linux

Para ejecutar el programa siga las siguientes instrucciones:

1. abrir el archivo rc.local con el comando:  sudo gedit /etc/rc.local 
2. agregar la siguiente linea "chmod 777 /sys/class/backlight/intel_backlight/brightness" antes de "exit 0"
3. Guardar los cambios.
