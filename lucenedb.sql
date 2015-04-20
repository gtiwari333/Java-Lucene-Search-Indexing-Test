CREATE TABLE IF NOT EXISTS `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(256) NOT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;


INSERT INTO `book` (`book_id`, `book_name`) VALUES
(1, 'Computer Introduction'),
(2, 'Interfaces');

-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `chapters` (
  `chapter_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `content` varchar(1024) NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;


INSERT INTO `chapters` (`chapter_id`, `book_id`, `title`, `content`) VALUES
(1, 1, 'Introduction to Computers', 'A computer is a programmable machine designed to automatically carry out a sequence of arithmetic or logical operations. The particular sequence of operations can be changed readily, allowing the computer to solve more than one kind of problem. An important class of computer operations on some computing platforms is the accepting of input from human operators and the output of results formatted for human consumption. The interface between the computer and the human operator is known as the user interface.  Conventionally a computer consists of some form of memory, at least one element that carries out arithmetic and logic operations, and a sequencing and control unit that can change the order of operations based on the information that is stored. Peripheral devices allow information to be entered from an external source, and allow the results of operations to be sent out.'),
(2, 2, 'User Interface', 'The user interface, in the industrial design field of human–machine interaction, is the space where interaction between humans and machines occurs. The goal of interaction between a human and a machine at the user interface is effective operation and control of the machine, and feedback from the machine which aids the operator in making operational decisions. Examples of this broad concept of user interfaces include the interactive aspects of computer operating systems, hand tools, heavy machinery operator controls, and process controls. The design considerations applicable when creating user interfaces are related to or involve such disciplines as ergonomics and psychology.'),
(9, 2, 'Command line interfaces', 'Command line interfaces, where the user provides the input by typing a command string with the computer keyboard and the system provides output by printing text on the computer monitor. Used by programmers and system administrators, in engineering and scientific environments, and by technically advanced personal computer users.'),
(10, 2, 'Touch user interface', 'Touch user interface are graphical user interfaces using a touchpad or touchscreen display as a combined input and output device. They supplement or replace other forms of output with haptic feedback methods. Used in computerized simulators etc.'),
(7, 1, 'Programming Language', 'Programming languages provide various ways of specifying programs for computers to run. Unlike natural languages, programming languages are designed to permit no ambiguity and to be concise. They are purely written languages and are often difficult to read aloud. They are generally either translated into machine code by a compiler or an assembler before being run, or translated directly at run time by an interpreter. Sometimes programs are executed by a hybrid method of the two techniques.'),
(8, 2, 'Graphical user interfaces', 'Graphical user interfaces (GUI) accept input via devices such as computer keyboard and mouse and provide articulated graphical output on the computer monitor. There are at least two different principles widely used in GUI design: Object-oriented user interfaces (OOUIs) and application oriented interfaces');
