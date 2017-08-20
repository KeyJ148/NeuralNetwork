-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Авг 20 2017 г., 23:14
-- Версия сервера: 5.7.19-0ubuntu0.16.04.1
-- Версия PHP: 7.0.22-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `neural_network`
--

-- --------------------------------------------------------

--
-- Структура таблицы `neuron`
--

CREATE TABLE `neuron` (
  `id` int(11) NOT NULL,
  `layer` int(11) NOT NULL,
  `output_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Структура таблицы `neuron_synapses_input`
--

CREATE TABLE `neuron_synapses_input` (
  `id` int(11) NOT NULL,
  `neuron_id` int(11) NOT NULL,
  `synapse_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Структура таблицы `synapse`
--

CREATE TABLE `synapse` (
  `id` int(11) NOT NULL,
  `input_neuron_id` int(11) NOT NULL,
  `weight` double NOT NULL,
  `last_delta_weight` double NOT NULL,
  `input_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `neuron`
--
ALTER TABLE `neuron`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `neuron_synapses_input`
--
ALTER TABLE `neuron_synapses_input`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_hnxy3b69ucdtv48olfphkyt1p` (`synapse_id`),
  ADD KEY `FK_ogso7dtgn537gpa77ip4b0lgs` (`neuron_id`);

--
-- Индексы таблицы `synapse`
--
ALTER TABLE `synapse`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `neuron`
--
ALTER TABLE `neuron`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;
--
-- AUTO_INCREMENT для таблицы `neuron_synapses_input`
--
ALTER TABLE `neuron_synapses_input`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT для таблицы `synapse`
--
ALTER TABLE `synapse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=201;
--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `neuron_synapses_input`
--
ALTER TABLE `neuron_synapses_input`
  ADD CONSTRAINT `FK_hnxy3b69ucdtv48olfphkyt1p` FOREIGN KEY (`synapse_id`) REFERENCES `synapse` (`id`),
  ADD CONSTRAINT `FK_ogso7dtgn537gpa77ip4b0lgs` FOREIGN KEY (`neuron_id`) REFERENCES `neuron` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
