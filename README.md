# CS207_Arduino_Wechat_Jump_Game_Robot_Project

In this paper we describe a modification of an Arduino Wechat Jump game robot. The original project, developed by Zhihu pipiwa on zhihu.com, describes an Arduino controlled robot detects the role position and destination position and calculates the pressing time by scanning screenshot from the game. After all, calculating on the computer, Arduino will receive a pressing time and then use a servo to achieve press in order to gain more goals. The work will build on the original project with the additional effort of avoiding physical gadget deviation on pressing time.

# Team
The build team consists of:

@#1 Student: Jifeng Zheng<br>
@#2 Student: Xiaowei Liang




# Arduino_connection
![alt text](https://github.com/zanhaomima/207project/blob/master/cs207.png)


I. INTRODUCTION
This project was originally designed for the author to achieve higher goals for an addictive mobile-game called Wechat Jump. Players will have to measure the distance between the current role position to the destination position by guess and then pressing time on screen will determine how far your role jumps. If your role missed the destination, your goal will be reset to 0. Since it is almost impossible to get the highest goal by hand, author Pipiwa [1] designed this Arduino robot to get the highest goal by using a computer to calculate press time and sending it to Arduino. Arduino will base on the press time which sent by computer to call servo to press the screen and make jump happen.
Building upon the work of Zhihu Pipiwa [1], we propose the development of the original project with a more accurate press operation which can reduce the physical gadget caused deviation. When physical gadget caused deviation to add together, the game role will deviate from the right path and fail the game. We hope this reduction of deviation will help the author to achieve a higher score in Wechat Jump game.

II. NOVEL CONTRIBUTION
We aim to replicate the functionality of the original project with additional buttons that will provide minor adjustment for press time and a different material for the physical press. we notice that there will be a deviation of the right path due to some physical equipment. For this problem, we will try to change the component for pressing in order to make faster and more accurate jump; moreover, we will add button component for a minor adjustment for the jump. For example, if the physical deviation is inevitable, we can press a button to give half second longer press time; that will force chessman (game role) jump further and implement minor adjustment. All these implements will improve the stability and sustainability of this game robot.


III. MATERIALS REQUIRED
According to the project website, the following material is required:
● Arduino UNO
● A computer
 ● Arduino button component
● Touch Screen Pen
If the project is
met:
● Milestone 1
of chessman
● Milestone 2
board
● Milestone 3
● Milestone 4
position
● Milestone 5
● Milestone 6


IV. MILESTONES
kept properly on track, the following milestones should be
Nov 1st
Nov 8th
Not 15th Nov 21st
Improve the algorithm that captures the position Working on how to transit result to Arduino
Implement all codes and link it together Working on adjusting servo to best pressing
Adding button component and related code December 1st Experiment different material for press
Nov 28th
component and reduce the deviation
The last two milestones are core steps to improve the original project. If it is successfully implemented, our Arduino Wechat Jump game robot will get higher scores compared to the original project.
V. TEAM ROLES
My name is Zheng, Jifeng. I will settle most coding parts. In the coding part, I will to improve the accuracy of capturing game role by screenshot and transmit data from computer to Arduino.
My name is Liang, Xiaowei. I will handle most documentation and assist he to implement the code, particularly in Arduino side.
Furthermore, we will assemble all the components together. VI. SUMMARY
This project aims to improve the stability and sustainability of the original project; therefore, if this project turns out as proposed, this Arduino game robot will get a higher score and last longer in Wechat Jump.
VII. REFERENCES

Zhihu. (2018). ​Python play "jump" iOS+Win hardware implementation​. [online] Available at: https://zhuanlan.zhihu.com/p/32526110 [Accessed 23 Oct. 2018].
GitHub. (2018). ​wangshub/wechat_jump_game​. [online] Available at: https://github.com/wangshub/wechat_jump_game [Accessed 23 Oct. 2018].
