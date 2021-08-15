-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 17, 2021 at 08:29 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `course_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `adminstrator`
--

CREATE TABLE `adminstrator` (
  `admin_id` varchar(20) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `adminstrator`
--

INSERT INTO `adminstrator` (`admin_id`, `name`, `email`, `dob`, `gender`, `address`, `phone_no`, `password`) VALUES
('3000003', 'admin', 'admin@admin.com', '2621/12/22', 'Male', 'sdfdsf', 'sdfdf', '12345'),
('3000004', 'Lalit Mahato', 'lalitmto11@gmail.com', '2020/12/12', 'Male', 'godar', '9865404842', '12345'),
('3000005', 'admin', 'admin', '6516/44/4', 'Male', 'sfdsf', '0942353', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `course_name`) VALUES
(1, 'BBA 1'),
(2, 'BIT'),
(3, 'Newtworking'),
(11, 'Business 1');

-- --------------------------------------------------------

--
-- Table structure for table `enroll`
--

CREATE TABLE `enroll` (
  `enroll_id` int(11) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  `module_type` varchar(30) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `semester` varchar(30) DEFAULT NULL,
  `instructor` varchar(30) DEFAULT NULL,
  `ins_id` varchar(20) DEFAULT NULL,
  `stud_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `ins_id` varchar(20) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`ins_id`, `name`, `email`, `dob`, `gender`, `address`, `phone_no`, `password`) VALUES
('2000006', 'Dipson Shrestha', 'dipson@gmail.com', '2050/12/22', 'Male', 'kathmandu', '9865404842', '12345'),
('2000007', 'Nirmal Thapa', 'nirmal@gmail.com', '2055/12/25', 'Male', 'kathmandu', '9865404842', '12345'),
('2000008', 'admin', 'admin@gmail.com', '2120/12/12', 'Male', 'nawalparasi', '94569894465', '12345'),
('2000009', 'admin1', 'admin1@gmail.com', '6566216216', 'Female', 'sdfkkdf', 'dfdf', '12345'),
('2000010', 'admin', 'admin', '1254/45/45', 'Male', 'dsdf', '9865404842', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `marks`
--

CREATE TABLE `marks` (
  `marks_id` int(11) NOT NULL,
  `stud_id` varchar(20) DEFAULT NULL,
  `student_name` varchar(50) DEFAULT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `marks` int(11) DEFAULT NULL,
  `remarks` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  `instructor` varchar(30) DEFAULT NULL,
  `module_type` varchar(30) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `semester` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`module_id`, `module_name`, `course_name`, `instructor`, `module_type`, `level`, `semester`) VALUES
(1, 'Introduction to programming I', 'BIT', 'Dharma Raj Mahato', 'Compulsory', 4, 'Summer'),
(2, 'Introduction to programming II', 'BIT', 'Dharma Raj Mahato', 'Compulsory', 4, 'Summer'),
(3, 'C programming', 'BIT', 'Lalit Mahto', 'Compulsory', 4, 'Summer'),
(8, 'Introduction to Business', 'BBA', 'Dharma Raj Mahato', 'Compulsory', 4, 'Summer'),
(11, 'Web Development 1', 'BBA', 'Lalit Mahto', 'Compulsory', 4, 'Summer'),
(12, 'Business Accounting', 'BBA', 'Shambhu Chaudhary', 'Compulsory', 4, 'Summer'),
(13, 'Business Accounting 2', 'BBA', 'Lalit Mahto', 'Compulsory', 4, 'Summer');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `stud_id` varchar(20) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `father_name` varchar(30) DEFAULT NULL,
  `mother_name` varchar(30) DEFAULT NULL,
  `parent_phone` varchar(30) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `level` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`stud_id`, `name`, `email`, `dob`, `gender`, `address`, `phone_no`, `father_name`, `mother_name`, `parent_phone`, `course`, `password`, `level`) VALUES
('1000004', 'Lalit Mahato', 'rklamahato11@gmail.com', '2055/12/07', 'Male', 'kawasoti', '9819469599', 'Ramananda Mahato', 'Khem Kumari Mahato', '9865404842', 'BIT', '12345', 4),
('1000005', 'Abinash Mahto', 'ab@gmail.com', '2012/12/4', 'Male', 'godar', '9819655521', 'ram', 'khem', '164965665', 'BIT', '12345', 4),
('1000006', 'Khusbu Mahto', 'khusbumh12@gmail.com', '2055/12/29', 'Female', 'Avawan', '9811588497', 'moti', 'radhika', '98155215', 'BIT', '12345', 4),
('1000007', 'admin', 'admin', '2161/51/45', 'Male', 'dkfld', 'dfdf', 'sdfdsf', 'dfdsf', 'fdsfdf', 'BIT', 'admin', 4),
('1000008', 'Ashish Giri', 'ashish@gmail.com', '2555441', 'Male', 'dfgfdg', 'gfhgfh', 'fghgfh', 'fghfg', 'fghgf', 'BIT', '12345', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adminstrator`
--
ALTER TABLE `adminstrator`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `enroll`
--
ALTER TABLE `enroll`
  ADD PRIMARY KEY (`enroll_id`),
  ADD UNIQUE KEY `enroll_id` (`enroll_id`),
  ADD KEY `ins_id` (`ins_id`),
  ADD KEY `stud_id` (`stud_id`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`ins_id`);

--
-- Indexes for table `marks`
--
ALTER TABLE `marks`
  ADD PRIMARY KEY (`marks_id`),
  ADD KEY `module_id` (`module_id`),
  ADD KEY `stud_id` (`stud_id`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`stud_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `enroll`
--
ALTER TABLE `enroll`
  MODIFY `enroll_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `marks`
--
ALTER TABLE `marks`
  MODIFY `marks_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enroll`
--
ALTER TABLE `enroll`
  ADD CONSTRAINT `enroll_ibfk_1` FOREIGN KEY (`ins_id`) REFERENCES `instructor` (`ins_id`),
  ADD CONSTRAINT `enroll_ibfk_2` FOREIGN KEY (`stud_id`) REFERENCES `student` (`stud_id`);

--
-- Constraints for table `marks`
--
ALTER TABLE `marks`
  ADD CONSTRAINT `marks_ibfk_1` FOREIGN KEY (`module_id`) REFERENCES `module` (`module_id`),
  ADD CONSTRAINT `marks_ibfk_2` FOREIGN KEY (`stud_id`) REFERENCES `student` (`stud_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
