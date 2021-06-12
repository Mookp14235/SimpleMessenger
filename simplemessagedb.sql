-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2020 at 05:58 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `simplemessagedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `mailbox`
--

CREATE TABLE `mailbox` (
  `id_message` int(11) NOT NULL,
  `sender` varchar(30) COLLATE utf16_unicode_ci NOT NULL,
  `receiver` varchar(30) COLLATE utf16_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `mailbox`
--

INSERT INTO `mailbox` (`id_message`, `sender`, `receiver`) VALUES
(3, 'akry_007@MiMin.ac.th', 'mookp.pppp@email.com'),
(4, 'oodora', 'mookp.pppp@email.com'),
(7, 'akry_007@MiMin.ac.th', 'oodora'),
(8, 'akry_007@MiMin.ac.th', 'mookp.pppp@email.com'),
(9, 'akry_007@MiMin.ac.th', 'akry_007@MiMin.ac.th'),
(12, 'ark', 'akry_007@MiMin.ac.th'),
(19, 'arky', 'mookp.pppp@email.com'),
(20, 'arky', 'sawarudo'),
(21, 'sawarudo', 'arky');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `subject` varchar(50) COLLATE utf16_unicode_ci NOT NULL,
  `body` longtext COLLATE utf16_unicode_ci NOT NULL,
  `statusRead` tinyint(1) NOT NULL,
  `senderDeleteStatus` tinyint(1) NOT NULL,
  `receiverDeleteStatus` tinyint(1) NOT NULL,
  `sDelete` tinyint(1) NOT NULL,
  `rDelete` tinyint(1) NOT NULL,
  `datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `subject`, `body`, `statusRead`, `senderDeleteStatus`, `receiverDeleteStatus`, `sDelete`, `rDelete`, `datetime`) VALUES
(3, 'as', 'asd\n', 0, 0, 0, 0, 0, '2020-04-16 21:47:28'),
(4, 'asd', 'asd\ndsa\n', 1, 0, 0, 0, 0, '2020-04-16 21:53:17'),
(7, 'pond', 'Meaw\n', 0, 0, 0, 0, 0, '2020-04-19 05:26:05'),
(8, 'ood', 'Test\r\n', 0, 0, 0, 0, 0, '2020-04-20 19:01:24'),
(9, 'hi ood', 'wow \nit has something worng\n', 0, 0, 0, 0, 0, '2020-04-20 20:34:35'),
(12, 'ood ending', 'goodbye\n', 0, 0, 0, 0, 0, '2020-04-20 22:37:22'),
(19, 'a', 'a\n', 0, 1, 0, 1, 0, '2020-05-19 18:50:17'),
(20, 'hi', 'hi newbie\n', 0, 1, 0, 1, 0, '2020-05-21 15:47:06'),
(21, 'hi', 'bro\n', 1, 0, 1, 0, 0, '2020-05-21 15:48:24');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID` int(10) NOT NULL,
  `fname` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  `lname` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf16_unicode_ci DEFAULT NULL,
  `password` varchar(20) COLLATE utf16_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID`, `fname`, `lname`, `email`, `password`) VALUES
(1, 'Akry', 'MiMiNiNi', 'akry_007@MiMin.ac.th', '1234'),
(2, 'Mookp', 'pppp', 'mookp.pppp@email.com', '12345'),
(3, 'bibi', 'Ora', 'bibi', '1234'),
(4, 'bibi1', 'Ora1', 'bibi1', '1234'),
(5, 'pipi', 'oraora', 'ora', '1234'),
(6, 'ood', 'ood', 'oodora', '1234'),
(7, 'arky', '1234', 'arky', '1234'),
(8, 'akry', '123', 'akry', 'akry'),
(9, 'akiraJang', 'MiMo', 'akiraJ', '1234'),
(10, 'akiraJ', 'ana', '1234', '1234'),
(12, 'amumu', 'ama', 'amumu', '1234'),
(13, 'akiry', 'rykia', 'ark', '1234'),
(14, 'mimi', 'mimi', 'sawarudo', '1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mailbox`
--
ALTER TABLE `mailbox`
  ADD PRIMARY KEY (`id_message`),
  ADD KEY `id_sender` (`sender`),
  ADD KEY `id_receiver` (`receiver`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
