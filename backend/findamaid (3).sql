-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2025 at 10:25 AM
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

INSERT INTO `maid_posts` (`id`, `user_id`, `name`, `age`, `location`, `experience`, `created_at`, `phone_number`, `expected_salary`, `working_hours`, `category`, `photo_url`, `photo`, `image_url`) VALUES
(1, 1, 'Sita', 30, NULL, NULL, '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', NULL, NULL, NULL),
(2, 2, 'Sita', 30, NULL, NULL, '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', NULL, NULL, NULL),
(3, 3, 'Sita', 30, NULL, NULL, '2025-07-13 14:14:00', '9876543210', 8500.00, '3 hours', 'Cooking', NULL, NULL, NULL),
(4, 4, 'Sita', 30, NULL, NULL, '2025-07-13 14:14:00', '9876500000', 9000.00, '2 hours', 'Dishwashing', NULL, '685cc20e1c9e1_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(5, 5, 'Nafiya', 25, NULL, NULL, '2025-07-13 14:14:00', '9876543222', 5000.00, '3 hours', 'Home Cleaning', NULL, '685cd79448464_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(6, 6, 'thara', 30, 'Chennai', '5 years', '2025-07-13 14:14:00', '9876543211', 10000.00, '2 hours', 'Cooking', NULL, '685ce112de03d_WhatsApp Image 2025-06-24 at 9.56.23 AM.jpeg', NULL),
(7, 7, 'Second User', 28, 'Chennai', '3 Years', '2025-07-13 14:14:00', '9999999999', 6000.00, '4 hours', 'Cooking', NULL, '68637027c18e7_image.jpg', NULL),
(8, 8, 'leo', 39, 'Chennai', '5 years', '2025-07-13 14:14:00', '9876543299', 10000.00, '2 hours', 'Cooking', NULL, '68637760a7706_image.jpg', NULL),
(9, 9, 'sam', 55, 'kanchipuram', '5 Years', '2025-07-13 14:14:00', '69399558855', 5000.00, '5+ hours', 'Clothes Washing', NULL, '68637ab732d6a_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png', NULL),
(10, 10, 'sam', 36, 'kanchipuram', '5 Years', '2025-07-13 14:14:00', '3639585855', 6000.00, '5+ hours', 'All Work', NULL, '1751447054_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(11, 11, 'sam', 36, 'kanchipuram', '5 Years', '2025-07-13 14:14:00', '6958382570', 5000.00, '5+ hours', 'Clothes Washing', NULL, '1751617671_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(12, 12, 'sam', 58, 'Chennai', '5 Years', '2025-07-13 14:14:00', '6969695855', 5000.00, '5+ hours', 'Clothes Washing', NULL, '1751618641_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(13, 13, 'lisa', 36, 'kanchipuram', '5 Years', '2025-07-13 14:14:00', '6939585824', 5000.00, '5+ hours', 'Cooking', NULL, '1751862450_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(14, 14, 'lalisa', 60, 'Chennai', '6 years', '2025-07-13 14:14:00', '6969353535', 10000.00, '5+ hours', 'Cooking', NULL, '1751862840_ChatGPT_Image_Jun_24__2025__08_42_40_AM.png', NULL),
(15, 15, 'jai', 65, 'Chennai', '6 years', '2025-07-13 14:14:00', '6969696935', 6000.00, '5+ hours', 'Cooking', NULL, '1751864020_ChatGPT_Image_Jun_23__2025__01_11_07_PM.png', NULL),
(16, 16, 'lisa', 52, 'Chennai', '5 Years', '2025-07-13 14:14:00', '6969693528', 6000.00, '5+ hours', 'All Work', NULL, '1751952752_ChatGPT_Image_Jun_24__2025__09_55_53_AM.png', NULL),
(17, 17, 'Sofiya', 28, 'Chennai', '3 years', '2025-07-13 15:04:20', '9876541111', 5000.00, '4 hours', 'Clothes Washing', '1752419060_cloathwashing.jpg', NULL, NULL),
(18, 2, 'Sim', 36, 'Chennai', '5 years', '2025-07-14 03:51:50', '9876511111', 8000.00, '3 hours', 'Cooking', '1752465110_maid1.jpeg', '1752465110_maid1.jpeg', NULL),
(19, 2, 'rani', 30, 'Chennai', '5 years', '2025-07-14 04:19:13', '9876222222', 8500.00, '3 hours', 'Cooking', '1752466753_maid1.jpeg', NULL, NULL),
(20, 2, 'rani1', 30, 'Chennai', '5 years', '2025-07-14 04:26:24', '9876222223', 8500.00, '3 hours', 'Cooking', '1752467184_maid1.jpeg', NULL, NULL),
(21, 13, 'ranii', 36, 'Chennai', '5 Years', '2025-07-14 04:31:21', '6355555588', 6000.00, '5+ hours', 'All Work', '1752467481_ChatGPT_Image_Jun_24__2025__09_51_05_AM.png', NULL, NULL),
(22, 13, 'sathya', 69, 'Chennai', '5 Years', '2025-07-14 04:39:46', '9999955555', 6000.00, '5+ hours', 'Home Cleaning', '1752467986_ChatGPT_Image_Jun_24__2025__09_51_05_AM.png', NULL, NULL),
(23, 10, 'josh', 56, 'Chennai', '5 Years', '2025-07-25 06:18:24', '6565654848', 5000.00, '4 hours', 'All Work', '1753424304_ChatGPT_Image_Jun_24__2025__09_51_05_AM.png', NULL, NULL);

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
(6, 1, 'Sofiya', 1, 5, 'Very punctual and good service', '2025-07-13 17:31:17'),
(7, 2, 'sam', 8, 5, 'good ', '2025-07-13 17:31:17'),
(8, 3, 'sam', 5, 5, 'great worker', '2025-07-13 17:31:17'),
(9, 10, NULL, 7, 5, 'Very punctual and polite', '2025-07-13 17:43:27'),
(10, 9, 'Sofiya', 10, 3, 'good', '2025-07-24 06:06:58'),
(11, 9, 'Sofiya', 10, 4, 'good', '2025-07-24 06:17:39'),
(12, 9, 'Sofiya', 10, 4, 'good', '2025-07-24 06:26:35'),
(13, 9, 'Sofiya', 10, 5, 'good', '2025-07-24 06:32:42'),
(14, 9, 'Sofiya', 10, 5, 'good', '2025-07-24 06:32:42'),
(15, 16, 'Sofiya', 10, 4, 'good', '2025-07-25 03:12:50'),
(16, 9, 'Sofiya', 10, 4, 'good', '2025-07-25 04:17:45'),
(17, 9, 'Sofiya', 10, 3, 'excellent', '2025-07-25 04:20:08'),
(18, 9, 'lisa', 9, 4, 'very good maid', '2025-07-25 04:23:53'),
(19, 9, 'Sofiya', 10, 4, 'good', '2025-09-10 08:10:28');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `id` int(11) NOT NULL,
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

INSERT INTO `signup` (`id`, `name`, `username`, `email`, `password`, `contact_number`, `profile_image`) VALUES
(1, 'Priya', 'priya123', 'priya@mail.com', '$2y$10$fTV2dYdRTGYOUG9U6xBFZeeoGlIHUMRt95mxmRHVhFVn9Y1ros19m', '9876543210', NULL),
(2, 'Trisha', 'Trisha123', 'trisha123@gmail.com', '$2y$10$dPdj4bWr3xdb5AbuaIaIteF/KKdLYjo5q0T13VQti7MJ5gXBrHoWy', '9656253513', ''),
(3, 'harini', 'hari123', 'harini1@gmail.com', '$2y$10$/crXOkFmYIPx.Se4VSNWLeVud.XhOTbWvYH9vAqpOLqntvL55huZ2', '6699669963', ''),
(4, 'jeo', 'jeo123', 'jeo322@gmail.com', '$2y$10$SIyzQGYFnsNJ8uBPZyyW8eqGNsQ6FuLK2WGyKWBbNHqlnDTeVsftG', '6939395828', NULL),
(5, 'leo', 'leo123', 'leo22@gmail.com', '$2y$10$QphIaKxfiisTksxft/ntp.9lzoeT41cmgww6.Ea52Ukj7/tIpIS4S', '6969325845', 'uploads/68622d899fb69_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png'),
(6, 'sai', 'sai123', 'sai22@gmail.com', '$2y$10$HUCeYwtAca/bBu8Oa.ooSe2C7m1Jr4Tadv35p.tbB7fDjLNGxbLa.', '6969585825', 'uploads/68638788ba444_ChatGPT Image Jun 23, 2025, 01_11_07 PM.png'),
(7, 'reya', 'reya22', 'reya222@gmail.com', '$2y$10$HsQuyFqtG6wXE4ZmjfCMOen1Agoz5gr0kN3gRiYwsXCcXi/DlVWVO', '6969692525', 'uploads/68639ed74f340_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png'),
(8, 'reo', 'reo22', 'reo222@gmail.com', '$2y$10$qe8EQKFCzsLK3ckYCUdJX.BynoE3We92zp0nJdJ/SiMylPQ1kah7y', '6969693588', 'uploads/6863a51acd063_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png'),
(9, 'lisa', 'lisa22', 'list22@gmail.com', '$2y$10$A0j/GeAvncgG6K4o4LBI2eiBG.1q0RONUe5x38aKoqRN.89pDkbrW', '69698568858', 'uploads/686cad205ee99_ChatGPT Image Jun 24, 2025, 09_55_53 AM.png'),
(10, 'Sofiya', 'sofiya123', 'sofiya@example.com', '$2y$10$A0j/GeAvncgG6K4o4LBI2eiBG.1q0RONUe5x38aKoqRN.89pDkbrW', '9876522222', 'uploads/6873cb91ae09b_Ellipse_1__1_.png'),
(11, 'shin', 'shin123', 'shin123@gmail.com', '$2y$10$F.nFnCKeg2VEEus7KAJ7R.WkL5RqqNqSRhbSzjL2dhG5AENDE9C1G', '6969666666', 'uploads/68747bbfd2bde__yogalife.jpeg'),
(12, 'shin', 'shin22', 'shin22@gmail.com', '$2y$10$3YW8xaiJ5YMao8wvTISyfO5I0l8JdEHJAVQNlBG0.KeWICmKdewuC', '6969667777', 'uploads/68747be8b1d7f__yogalife.jpeg'),
(13, 'sam', 'sam24', 'sam24@gmail.com', '$2y$10$jY8JnQr607PVHLt5PhTNtOuxdD7mE/BBoa9j4mwl6r2juJIoJLWaq', '6533333333', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_addresses`
--

CREATE TABLE `user_addresses` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `maid_post_id` int(11) NOT NULL,
  `address` text NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `status` enum('pending','accepted','rejected') DEFAULT 'pending',
  `latitude` varchar(50) DEFAULT NULL,
  `longitude` varchar(50) DEFAULT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_addresses`
--

INSERT INTO `user_addresses` (`id`, `user_id`, `maid_post_id`, `address`, `pincode`, `status`, `latitude`, `longitude`, `submitted_at`, `created_at`) VALUES
(1, 1, 1, '12, Gandhi Street, T Nagar, Chennai', '600017', 'pending', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(2, 1, 2, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', 'pending', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(3, 1, 3, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', 'pending', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(4, 1, 4, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', 'pending', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(5, 1, 5, '22G8+M93, Kuthambakkam, Tamil Nadu 600124, India', '600124', 'accepted', NULL, NULL, '2025-07-13 16:00:01', '2025-07-13 15:49:30'),
(6, 1, 6, '42 Main Street, Chennai', '600001', 'pending', '13.0827', '80.2707', '2025-07-13 16:02:32', '2025-07-13 16:02:32'),
(7, 1, 101, '25, Gandhi Street, Chennai', '600032', 'pending', '13.0810', '80.2740', '2025-07-13 16:49:52', '2025-07-13 16:49:52'),
(8, 1, 10, '101 Gandhi Street, Chennai', '600032', 'pending', '13.0810', '80.2740', '2025-07-13 17:15:38', '2025-07-13 17:15:38'),
(9, 1, 10, '125 Gandhi Street, Chennai', '600032', 'accepted', '13.0810', '80.2740', '2025-07-13 17:50:27', '2025-07-13 17:50:27');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `maid_posts`
--
ALTER TABLE `maid_posts`
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `user_addresses`
--
ALTER TABLE `user_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
