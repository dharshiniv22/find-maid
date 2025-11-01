-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 28, 2025 at 05:30 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `findamaid`
--

-- --------------------------------------------------------

--
-- Table structure for table `maid_posts`
--

CREATE TABLE `maid_posts` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int(11) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `experience` varchar(100) DEFAULT NULL,
  `language_known` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `phone_number` varchar(15) NOT NULL,
  `expected_salary` decimal(10,2) NOT NULL,
  `working_hours` enum('1 hour','2 hours','3 hours','4 hours','5+ hours') NOT NULL,
  `category` enum('Clothes Washing','Cooking','Home Cleaning','Dishwashing','All Work') NOT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `maid_posts`
--

INSERT INTO `maid_posts` (`id`, `user_id`, `name`, `age`, `location`, `experience`, `language_known`, `created_at`, `phone_number`, `expected_salary`, `working_hours`, `category`, `photo_url`, `photo`, `image_url`) VALUES
(1, 1, 'Sita', 30, 'chennai', '1 year', 'Tamil, hindi', '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', 'maid71.jpg', NULL, NULL),
(2, 2, 'ranjini', 30, 'Chengalpattu', '6 months', 'tamil', '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', 'maid72.jpg', NULL, NULL),
(3, 3, 'lakshmi', 30, 'kanchipuram', '3 years', 'tamil, english', '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', 'maid73.jpg', NULL, NULL),
(4, 1, 'geetha', 30, 'chennai', '15 years', 'hindi, telugu', '2025-07-13 14:14:00', '9876500000', 9000.00, '2 hours', 'Dishwashing', 'maid20.png', '685cc20e1c9e1_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(5, 5, 'selvi', 25, 'Chengalpattu', '4 years', 'tamil, telugu', '2025-07-13 14:14:00', '9876543222', 5000.00, '3 hours', 'Home Cleaning', 'maid113.jpg', '685cd79448464_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(6, 6, 'thara', 30, 'Chennai', '5 years', 'tamil', '2025-07-13 14:14:00', '9876543211', 10000.00, '2 hours', 'Cooking', 'maid74.jpg', '685ce112de03d_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(7, 7, 'mohana', 28, 'Chennai', '3 Years', 'tamil', '2025-07-13 14:14:00', '9999999999', 6000.00, '4 hours', 'Cooking', 'maid76.jpg', '68637027c18e7_image.jpg', NULL),
(8, 8, 'moni', 39, 'Chennai', '5 years', 'tamil, english', '2025-07-13 14:14:00', '9876543299', 10000.00, '2 hours', 'Cooking', 'maid77.jpg', '68637760a7706_image.jpg', NULL),
(9, 9, 'jamuna', 55, 'kanchipuram', '5 Years', 'tamil,, telugu', '2025-07-13 14:14:00', '69399558855', 5000.00, '5+ hours', 'Clothes Washing', 'maid23.png', '68637ab732d6a_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png', NULL),
(10, 10, 'pooja', 36, 'kanchipuram', '5 Years', 'tamil, telugu', '2025-07-13 14:14:00', '3639585855', 6000.00, '5+ hours', 'All Work', 'maid112.jpg', '1751447054_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(11, 11, 'deeba', 36, 'kanchipuram', '5 Years', 'tamil, telugu', '2025-07-13 14:14:00', '6958382570', 5000.00, '5+ hours', 'Clothes Washing', 'maid24.png', '1751617671_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(12, 12, 'prema', 58, 'Chennai', '5 Years', 'tamil', '2025-07-13 14:14:00', '6969695855', 5000.00, '5+ hours', 'Clothes Washing', '25.png', '1751618641_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(13, 13, 'uma', 36, 'kanchipuram', '5 Years', 'tamil, english', '2025-07-13 14:14:00', '6939585824', 5000.00, '5+ hours', 'Cooking', 'maid78.jpg', '1751862450_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(14, 14, 'sangeetha', 60, 'Chennai', '6 years', 'tamil', '2025-07-13 14:14:00', '6969353535', 10000.00, '5+ hours', 'Cooking', 'maid79.jpg', '1751862840_ChatGPT_Image_Jun_24__2025__08_42_40_AM.png', NULL),
(15, 15, 'logasri', 65, 'Chennai', '6 years', 'tamil', '2025-07-13 14:14:00', '6969696935', 6000.00, '5+ hours', 'Cooking', 'maid80.jpg', '1751864020_ChatGPT_Image_Jun_23__2025__01_11_07_PM.png', NULL),
(16, 16, 'shobana', 52, 'Chennai', '5 Years', 'tamil, telugu', '2025-07-13 14:14:00', '6969693528', 6000.00, '5+ hours', 'All Work', 'maid27.png', '1751952752_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(17, 17, 'Sofiya', 28, 'Chennai', '3 years', 'english, tamil', '2025-07-13 15:04:20', '9876541111', 5000.00, '4 hours', 'Clothes Washing', 'maid78.jpg', NULL, NULL),
(18, 18, 'sundari', 36, 'Chennai', '5 years', 'english, tamil', '2025-07-14 03:51:50', '9876511111', 8000.00, '3 hours', 'Cooking', 'maid111.jpg', '1752465110_maid1.jpeg', NULL),
(19, 19, 'rani', 30, 'Chennai', '5 years', 'tamil', '2025-07-14 04:19:13', '9876222222', 8500.00, '3 hours', 'Cooking', 'maid81.jpg', NULL, NULL),
(21, 21, 'Adhirai', 36, 'Chennai', '5 Years', 'tamil, hindi', '2025-07-14 04:31:21', '6355555588', 6000.00, '5+ hours', 'All Work', 'maid28.png', NULL, NULL),
(22, 22, 'sathya', 69, 'Chennai', '5 Years', 'tamil', '2025-07-14 04:39:46', '9999955555', 6000.00, '5+ hours', 'Home Cleaning', 'maid78.jpg', NULL, NULL),
(23, 23, 'Dhana', 56, 'Chennai', '5 Years', 'tamil', '2025-07-25 06:18:24', '6565654848', 5000.00, '4 hours', 'All Work', 'maid29.png', NULL, NULL),
(24, 24, 'shalaini', 56, 'kanchipuram', '5 yrs', 'tamil, telugu', '2025-09-22 14:50:15', '6969585847', 5000.00, '1 hour', 'Clothes Washing', 'maid26.png', NULL, NULL),
(25, 25, 'saroja', 35, 'Chennai', '4 yrs', 'tamil', '2025-09-22 14:52:32', '2558475848', 5000.00, '5+ hours', 'Dishwashing', 'maid21.png', NULL, NULL),
(26, 26, 'kavitha', 54, 'Chennai', '5 yrs', 'tamil', '2025-09-24 03:28:56', '6845454545', 5000.00, '5+ hours', 'Clothes Washing', 'maid75.jpg', NULL, NULL),
(27, 2, 'Kavitha R', 30, 'Chennai', '5 years', 'hindi, tamil', '2025-10-08 11:01:10', '9876540000', 12000.00, '', 'Cooking', '1759921270_ranii.jpg', NULL, NULL),
(28, 28, 'kavitha', 44, 'Chennai', '5 yrs', 'tamil', '2025-10-09 15:26:57', '9696969550', 5000.00, '5+ hours', 'Home Cleaning', 'maid114.jpg', NULL, NULL),
(29, 17, 'chitra', 65, 'chennai', '5 years', 'Tamil, Kannada', '2025-10-25 21:09:33', '6245425424', 9000.00, '5+ hours', 'Dishwashing', 'maid22.png', NULL, NULL),
(30, 18, 'kavitha', 56, 'chennai', '5 years', 'Tamil, English', '2025-10-26 15:11:14', '6585855488', 90000.00, '4 hours', 'All Work', '1761491474_IMG-20251008-WA0025.jpg', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `maid_post_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `maid_post_id` int(11) NOT NULL,
  `maid_name` varchar(100) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reviews`
--

INSERT INTO `reviews` (`id`, `maid_post_id`, `maid_name`, `user_id`, `rating`, `comment`, `created_at`) VALUES
(6, 1, 'ramani', 1, 5, 'Very punctual and good service', '2025-07-13 17:31:17'),
(7, 2, 'lavanya', 8, 5, 'good ', '2025-07-13 17:31:17'),
(8, 3, 'abi', 5, 5, 'great worker', '2025-07-13 17:31:17'),
(9, 10, 'nirai', 7, 5, 'Very punctual and polite', '2025-07-13 17:43:27'),
(15, 16, 'nafiya', 10, 4, 'good', '2025-07-25 03:12:50'),
(17, 9, 'sharmi', 10, 3, 'excellent', '2025-07-25 04:20:08'),
(18, 9, 'lali', 9, 4, 'very good maid', '2025-07-25 04:23:53'),
(19, 9, 'ramya', 10, 4, 'good', '2025-09-10 08:10:28'),
(25, 16, 'Sofiya', 10, 4, 'great work', '2025-09-23 15:16:54'),
(26, 16, 'oviya', 10, 4, 'great work', '2025-09-23 15:16:54'),
(27, 16, 'kaviya', 10, 4, 'great work', '2025-09-23 15:16:54'),
(28, 16, 'kalai', 10, 4, 'great work', '2025-09-23 15:16:55'),
(31, 9, 'Sofiya', 10, 5, 'good', '2025-09-24 03:27:29'),
(32, 9, 'jeo', 18, 4, 'good working', '2025-10-26 19:55:24'),
(33, 1, 'jeo', 18, 4, 'very punctual', '2025-10-26 19:57:53');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `maid_post_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`id`, `user_id`, `maid_post_id`, `name`, `username`, `email`, `password`, `contact_number`, `profile_image`) VALUES
(1, 1, 1, 'Priya', 'priya123', 'priya@mail.com', '$2y$10$fTV2dYdRTGYOUG9U6xBFZeeoGlIHUMRt95mxmRHVhFVn9Y1ros19m', '9876543210', 'p1.png'),
(2, 2, 2, 'Trisha', 'Trisha123', 'trisha123@gmail.com', '$2y$10$dPdj4bWr3xdb5AbuaIaIteF/KKdLYjo5q0T13VQti7MJ5gXBrHoWy', '9656253513', 'p2.png'),
(3, 3, 3, 'harini', 'hari123', 'harini1@gmail.com', '$2y$10$/crXOkFmYIPx.Se4VSNWLeVud.XhOTbWvYH9vAqpOLqntvL55huZ2', '6699669963', 'p3.png'),
(4, 4, 4, 'jeo', 'jeo123', 'jeo322@gmail.com', '$2y$10$SIyzQGYFnsNJ8uBPZyyW8eqGNsQ6FuLK2WGyKWBbNHqlnDTeVsftG', '6939395828', 'p4.png'),
(5, 5, 5, 'leo', 'leo123', 'leo22@gmail.com', '$2y$10$QphIaKxfiisTksxft/ntp.9lzoeT41cmgww6.Ea52Ukj7/tIpIS4S', '6969325845', 'p5.png'),
(6, 6, 6, 'sai', 'sai123', 'sai22@gmail.com', '$2y$10$HUCeYwtAca/bBu8Oa.ooSe2C7m1Jr4Tadv35p.tbB7fDjLNGxbLa.', '6969585825', 'p6.png'),
(7, 7, 7, 'reya', 'reya22', 'reya222@gmail.com', '$2y$10$HsQuyFqtG6wXE4ZmjfCMOen1Agoz5gr0kN3gRiYwsXCcXi/DlVWVO', '6969692525', 'p7.png'),
(8, 8, 8, 'reo', 'reo22', 'reo222@gmail.com', '$2y$10$qe8EQKFCzsLK3ckYCUdJX.BynoE3We92zp0nJdJ/SiMylPQ1kah7y', '6969693588', 'p8.png'),
(9, 9, 9, 'lisa', 'lisa22', 'list22@gmail.com', '$2y$10$A0j/GeAvncgG6K4o4LBI2eiBG.1q0RONUe5x38aKoqRN.89pDkbrW', '69698568858', 'p9.png'),
(10, 10, 10, 'Sofiya', 'sofiya123', 'sofiya@example.com', '$2y$10$A0j/GeAvncgG6K4o4LBI2eiBG.1q0RONUe5x38aKoqRN.89pDkbrW', '9876522222', 'p12.png'),
(11, 11, 11, 'shin', 'shin123', 'shin123@gmail.com', '$2y$10$F.nFnCKeg2VEEus7KAJ7R.WkL5RqqNqSRhbSzjL2dhG5AENDE9C1G', '6969666666', 'p11.png'),
(12, 12, 12, 'shin', 'shin22', 'shin22@gmail.com', '$2y$10$3YW8xaiJ5YMao8wvTISyfO5I0l8JdEHJAVQNlBG0.KeWICmKdewuC', '6969667777', 'p13.png'),
(13, 13, 13, 'sam', 'sam24', 'sam24@gmail.com', '$2y$10$jY8JnQr607PVHLt5PhTNtOuxdD7mE/BBoa9j4mwl6r2juJIoJLWaq', '6533333333', 'p14.png'),
(14, 14, 14, 'rajii', 'raji123', 'raji123@gmail.com', '$2y$10$8f22dttpLUSFArTzr50NzevtKmFhXlJjapKSyz5Bn2QhVkUTvOQOS', '6565626860', 'p15.png'),
(15, 15, 15, 'fitraa', 'fitraa22', 'fitraa22@gmail.com', '$2y$10$B5ov17.2tMlnuyTaF7XKxuphSgGVStDrCmKn1kkawecWOoHlzycHu', '6525252525', 'p16.png'),
(16, 16, 16, 'fitra', 'fitra24', 'fitra24@gmail.com', '$2y$10$zs2b5sWVSO0l1iAI2rPglexvnj16BDpCdXWGy.MefptJm4BtIKHXG', '6525252529', 'uploads/68ee63962feec_IMG-20251014-WA0097.jpg'),
(17, 17, 17, 'chitra', 'chitra17', 'chitra17@gmail.com', '$2y$10$8skzZxKe8NvDacNMM.x8LuC1OqrpsqbTt5FZKod7nnl8AWMmZK1wC', '9655555555', 'p17.png'),
(18, 18, 18, 'sangee', 'sange22', 'sangee22@gmail.com', '$2y$10$V8yggA/kuuyrUPkdy/zr0eerEvhJaTDHqdhVcHITQCSLJ/g/6XFqS', '8584868789', 'p10.png');

-- --------------------------------------------------------

--
-- Table structure for table `user_addresses`
--

CREATE TABLE `user_addresses` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `maid_post_id` int(11) NOT NULL,
  `address` text NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `preferred_time` datetime DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `status` enum('pending','accepted','rejected') DEFAULT 'pending',
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_addresses`
--

INSERT INTO `user_addresses` (`id`, `user_id`, `name`, `phone_number`, `maid_post_id`, `address`, `pincode`, `preferred_time`, `notes`, `status`, `latitude`, `longitude`, `submitted_at`, `created_at`) VALUES
(1, 1, 'gana', '6768696956', 1, '12, Gandhi Street, T Nagar, Chennai', '600017', NULL, NULL, 'accepted', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(2, 2, 'jaya', '9078907890', 2, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', NULL, NULL, 'accepted', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(3, 3, 'ramani', '6775858799', 3, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', NULL, NULL, 'pending', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(4, 4, 'narma', '9089786700', 4, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', NULL, NULL, 'rejected', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(5, 16, 'sofiya', '80000097656', 5, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', NULL, NULL, 'accepted', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(6, 6, 'roja', '9000986754', 6, '42 Main Street, Chennai', '600001', NULL, NULL, 'pending', '13.0827', '80.2707', '2025-07-13 16:02:32', '2025-07-13 16:02:32'),
(7, 7, 'nifashini', '90078564534', 101, '25, Gandhi Street, Chennai', '600032', NULL, NULL, 'pending', '13.0810', '80.2740', '2025-07-13 16:49:52', '2025-07-13 16:49:52'),
(8, 8, 'ranjini', '9089785645', 10, '101 Gandhi Street, Chennai', '600032', NULL, NULL, 'pending', '13.0810', '80.2740', '2025-07-13 17:15:38', '2025-07-13 17:15:38'),
(9, 9, 'kamali', '908999990', 10, '125 Gandhi Street, Chennai', '600032', NULL, NULL, '', '13.0810', '80.2740', '2025-07-13 17:50:27', '2025-07-13 17:50:27'),
(10, 1, 'Dhars', '9876543210', 5, '123, Gandhi Street, Chennai', '600017', '2025-10-12 18:30:00', 'Please call on arrival', '', '13.0827', '80.2707', '2025-10-12 16:52:13', '2025-10-12 16:52:13'),
(11, 101, 'ramya', '9876543210', 5, '123, Gandhi Street, Chennai', '600017', '2025-10-12 18:30:00', 'Please call on arrival', 'rejected', '13.0827', '80.2707', '2025-10-12 17:39:03', '2025-10-12 17:39:03'),
(12, 101, 'santhi', '9876543200', 5, '123, Gandhi Street, Chennai', '600017', '2025-10-12 18:30:00', 'Please call on arrival', 'accepted', '13.0827', '80.2707', '2025-10-12 17:49:44', '2025-10-12 17:49:44'),
(13, 17, 'sharmila', '6222233335', 28, 'saveetha university', '635204', '2025-10-19 01:45:00', '', 'pending', '', '', '2025-10-12 18:17:59', '2025-10-12 18:17:59'),
(14, 17, 'sharmi', '9655555555', 28, '75/ A annai nagar', '632081', '2025-10-19 12:55:00', '', 'pending', '', '', '2025-10-12 18:27:50', '2025-10-12 18:27:50'),
(15, 101, 'santhi', '9876543200', 5, '123, Gandhi Street, Chennai', '600017', '2025-10-12 18:30:00', 'Please call on arrival', 'pending', '13.0827', '80.2707', '2025-10-14 12:32:38', '2025-10-14 12:32:38'),
(16, 1, 'kamini', '6565454555', 28, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-14 10:14:00', 'please call on arrival', 'accepted', '12.8365047', '79.7037017', '2025-10-14 12:45:37', '2025-10-14 12:45:37'),
(17, 18, 'suji', '6969585800', 1, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-22 22:26:00', '', 'pending', '12.8365047', '79.7037017', '2025-10-22 16:57:28', '2025-10-22 16:57:28'),
(18, 10, 'valli', '6528584588', 1, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-24 03:50:00', '', 'pending', '12.8365047', '79.7037017', '2025-10-24 10:28:07', '2025-10-24 10:28:07'),
(19, 10, 'valli', '6528584588', 1, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-24 03:50:00', '', 'pending', '12.8365047', '79.7037017', '2025-10-24 10:28:07', '2025-10-24 10:28:07'),
(20, 10, 'sharmi', '6464646263', 22, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-24 16:04:00', '', 'accepted', '12.8365047', '79.7037017', '2025-10-24 10:35:12', '2025-10-24 10:35:12'),
(21, 10, 'sairanjini', '6869676969', 29, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-26 15:20:00', 'work hard', 'pending', '12.8365047', '79.7037017', '2025-10-25 21:12:39', '2025-10-25 21:12:39'),
(22, 18, 'shanili', '6956585522', 4, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-26 16:30:00', 'call me on arrival', 'pending', '12.8365047', '79.7037017', '2025-10-26 15:08:34', '2025-10-26 15:08:34'),
(23, 4, 'kalai', '6952520088', 30, '46, Kanchipuram - Chengalpattu Rd, Bavaji Nagar, Pillaiyarpalayam, Kanchipuram, Tamil Nadu 631502, India', '631502', '2025-10-26 04:45:00', 'call me on arrival', 'pending', '12.8365047', '79.7037017', '2025-10-26 15:13:18', '2025-10-26 15:13:18');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `maid_posts`
--
ALTER TABLE `maid_posts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_addresses`
--
ALTER TABLE `user_addresses`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `maid_posts`
--
ALTER TABLE `maid_posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user_addresses`
--
ALTER TABLE `user_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `signup` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
