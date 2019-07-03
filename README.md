# GeoQuiz
the application starts with a MainActivity that displays a question and,
(1) a pair of buttons of whether the question is true or false.
(2)a pair of image buttons that display next or previous question.
(3) a cheat button that takes the user to "CheatActivity".
if the user decides to cheat , MainActivity passes the answer to the question
to CheatActivity which has a confirmation button that the user really does want to cheat.
once clicked the answer will be shown , CheatActivity sends info whether user cheated or not 
to MainActivity , if he did cheat , a judgemental toast pops up whenever
he answers the question...
the application handles run-time configuration changes like screen rotation.
