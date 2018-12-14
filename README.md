# CS207_Arduino_Wechat_Jump_Game_Robot_Project

In this paper we describe a modification of an Arduino Wechat Jump game robot. The original project, developed by Zhihu pipiwa on zhihu.com, describes an Arduino controlled robot detects the role position and destination position and calculates the pressing time by scanning screenshot from the game. After all, calculating on the computer, Arduino will receive a pressing time and then use a servo to achieve press in order to gain more goals. The work will build on the original project with the additional effort of avoiding physical gadget deviation on pressing time.

# Instructor
Trevor Michael Tomesh

# Team
The build team consists of:

@#1 Student: Jifeng Zheng<br>
@#2 Student: Xiaowei Liang




# Arduino_connection
![alt text](https://github.com/zanhaomima/207project/blob/master/cs207.png)
![alt text](https://github.com/zanhaomima/207project/blob/master/image.png)

# User Manual for Auto-player

This WeChat Jump auto-player is fairly easy to use, however it does require some effort to set up and some programming knowledge. The first step would be to set up LCD screen and servo on Arduino breadboard and download the Arduino code from GitHub. After load Arduino code in to Arduino clip, the second step would be downloading all the java code into Eclipse and connect phone to computer in order share phone screen to computer. The last step and also the hardest step would be that different phone and PC need to adjust the screenshot size accordingly by change the parameters value in java code and cut the chess piece image out as a model by Photoshop. The last step is fairly hard for people who don’t have knowledge of computer but it is necessary for get an accurate result.
Furthermore, in our project set up, one end of a wire needs to be fixed on the servo and the other end should be plug in to a fruit and there will need a wet thin page or a wet sponge on the phone screen where the wire pin touches. This set-up is designed for simulating the process of how human finger press on capacitive screen. However, if a touch screen pen is possible to get, it will be less complicated to set up.
If everything is functioning, the only thing left to do is run the JumpJump.java and select the port for Arduino and run the program. After run the program, Arduino will jump by itself! it is important to note that all software changes screen color should be turned off to insure the accuracy of jump as the algorithm behind is use color to distinguish board and chess piece.

# Project Proposal
This project was originally designed for the author to achieve higher goals for an addictive mobile-game called Wechat Jump. Players will have to measure the distance between the current role position to the destination position by guess and then pressing time on screen will determine how far your role jumps. If your role missed the destination, your goal will be reset to 0. Since it is almost impossible to get the highest goal by hand, author Pipiwa [1] designed this Arduino robot to get the highest goal by using a computer to calculate press time and sending it to Arduino. Arduino will base on the press time which sent by computer to call servo to press the screen and make jump happen.
Building upon the work of Zhihu Pipiwa [1], we propose the development of the original project with a more accurate press operation which can reduce the physical gadget caused deviation. When physical gadget caused deviation to add together, the game role will deviate from the right path and fail the game. We hope this reduction of deviation will help the author to achieve a higher score in Wechat Jump game.


# MATERIALS REQUIRED
According to the project website, the following material is required:

● Arduino UNO

● A computer

● Arduino button component

● Touch Screen Pen



# REFERENCES

Zhihu. (2018). ​Python play "jump" iOS+Win hardware implementation​. [online] Available at: https://zhuanlan.zhihu.com/p/32526110 [Accessed 23 Oct. 2018].
GitHub. (2018). ​wangshub/wechat_jump_game​. [online] Available at: https://github.com/wangshub/wechat_jump_game [Accessed 23 Oct. 2018].
