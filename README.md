# IPFS_Mobile_Test

**OVERVIEW**

This is an alternate way to make your android apps unleash the full potential of IPFS.
Up till now there is no way to use the full IPFS functionality in your android apps, this is a simple workaround till the offical android IPFS client program is released.

What we make use of here is an app called termux, termux as stated on their website is an Android terminal emulator and Linux environment app, which means it can be used in our android device to run linux apps, we know that ipfs has a linux implementation, and the IPFS package has been added to the termux app's repos, so we can download it and use it to run a full blown IPFS node in our own smartphone! :) 

I had tested a few commands and all to see whether advanced commands of ipfs work, and luckily the ones i tested(socket communication) do work. Now that's all great, it works well, but the problem arose when my app wanted to communicate with termux app's ipfs commandline to execute ipfs commands. Since termux has not extended a service for my app to use via intents, I was not able to communicate, so what is done here is that, I made use of nodejs package in termux and built a server, that using node-cmd package, the server can execute commands on termux's commandline and return results. So what the above android app does is it communcates with termux via http requests and executes ipfs commands which are returned back to the app as result. So thus now  any app can run IPFS commands, all the commands even socket level commands such as opening a socket for communication via libp2p can be done by any 3rd party apps now.


**STEPS**

Install and open termux

vi setup.sh

Copy the contents of setup.sh from this repo to  setup.sh in termux

Do chmod 777 setup.sh

Execute the shell script using ./setup.sh  

This will create a start.sh and stop.sh to start and stop both the server code and ipfs daemon

Install the above app to the phone and click the button and a file would be created and sent to termux environment which will then be added to IPFS and the result of the command is returned back to the app.


