INSERT INTO `company` (`id`, `cnpj`, `updated_date`, `creation_date`, `company_name`) 
VALUES (NULL, '82198127000121', CURRENT_DATE(), CURRENT_DATE(), 'Kazale IT');

INSERT INTO `employee` (`id`, `social_security_number`, `updated_date`, `creation_date`, `email`, `name`, 
`profile`, `qtd_lunch_hours`, `qtd_work_day_hours`, `password`, `hour_value`, `company_id`) 
VALUES (NULL, '16248890935', CURRENT_DATE(), CURRENT_DATE(), 'admin@kazale.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL, 
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL, 
(SELECT `id` FROM `empresa` WHERE `cnpj` = '82198127000121'));