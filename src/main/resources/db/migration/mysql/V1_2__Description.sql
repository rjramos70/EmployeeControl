CREATE TABLE `company` (
  `id` bigint(20) NOT NULL,
  `cnpj` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL,
  `creation_date` datetime NOT NULL,
  `company_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL,
  `social_security_number` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL,
  `creation_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `profile` varchar(255) NOT NULL,
  `qtd_lunch_hours` float DEFAULT NULL,
  `qtd_work_day_hours` float DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `hour_value` decimal(19,2) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `launch` (
  `id` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `updated_date` datetime NOT NULL,
  `creation_date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4cm1kg523jlopyexjbmi6y54j` (`company_id`);

--
-- Indexes for table `launch`
--
ALTER TABLE `launch`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK46i4k5vl8wah7feutye9kbpi4` (`employee_id`);

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `launch`
--
ALTER TABLE `launch`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK4cm1kg523jlopyexjbmi6y54j` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Constraints for table `launch`
--
ALTER TABLE `launch`
  ADD CONSTRAINT `FK46i4k5vl8wah7feutye9kbpi4` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);
