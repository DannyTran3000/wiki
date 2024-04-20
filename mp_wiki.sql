-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 20, 2024 at 03:30 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mp_wiki`
--

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `fullname` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `access_token` varchar(255) DEFAULT NULL,
  `role` tinyint(4) NOT NULL DEFAULT 1 COMMENT '0: admin, 1: visitor',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `fullname`, `email`, `password`, `salt`, `access_token`, `role`, `created_at`, `updated_at`) VALUES
(18, 'Danny Tran', 'trandangminhnhat.dev@gmail.com', 'ni5uYFzV+isbpz1lUgz5zG5GMBzU53V54L3n+meEHhQ=', 'i96Fl6FVLqG28QtwteOFKA==', '9EaCalRhX/WyOZSrFE1m+Wiz08qNW6SNac0XhTWpWi0=', 1, '2024-04-17 21:51:07', NULL),
(25, 'Nhat Tran', 'test@gmail.com', 'Ur7nKzV3UxdRDnl7mk51FQ==', 'EBeUf+cEb8YZ0VgyhW2d+g==', 'OQNOlfOAFkibazgxhkzb2a7ruqjjjGMkq4I7wNtp/os=', 1, '2024-04-20 11:23:51', NULL),
(26, 'Admin', 'admin@wikiportal.com', 'eHpYQhgVGiI2diADVlbF7A==', 'CWMh8LF0ycBqgzEtLOCqng==', 'bF1i4JIWl1oTWIWFFxONL/lafKpEXQSk9tpIoBIW3IQ=', 0, '2024-04-20 11:28:03', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
