-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Время создания: Авг 20 2017 г., 23:13
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

--
-- Дамп данных таблицы `neuron`
--

INSERT INTO `neuron` (`id`, `layer`, `output_id`) VALUES
(112, 0, -1),
(113, 0, -1),
(114, 1, -1),
(115, 1, -1),
(116, 1, -1),
(117, 1, -1),
(118, 2, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `neuron_synapses_input`
--

CREATE TABLE `neuron_synapses_input` (
  `id` int(11) NOT NULL,
  `neuron_id` int(11) NOT NULL,
  `synapse_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `neuron_synapses_input`
--

INSERT INTO `neuron_synapses_input` (`id`, `neuron_id`, `synapse_id`) VALUES
(180, 112, 189),
(181, 113, 200),
(182, 114, 194),
(183, 114, 198),
(184, 115, 188),
(185, 115, 192),
(186, 116, 196),
(187, 116, 193),
(188, 117, 187),
(189, 117, 190),
(190, 118, 195),
(191, 118, 191),
(192, 118, 197),
(193, 118, 199);

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
-- Дамп данных таблицы `synapse`
--

INSERT INTO `synapse` (`id`, `input_neuron_id`, `weight`, `last_delta_weight`, `input_id`) VALUES
(187, 112, -2.1922968867078136, -0.0000007163764142505695, -1),
(188, 112, -19.52052217869875, -0.000004239258032696999, -1),
(189, -1, -1.787316478589917, 0, 0),
(190, 113, -2.412564267093265, -0.0000028036889456817624, -1),
(191, 115, 16.32000747278978, 0.000002999366886499818, -1),
(192, 113, 14.165918261902206, 0.0000065691290080684815, -1),
(193, 112, 22.558509411407144, 0.000017250518426971632, -1),
(194, 113, 2.329485414430085, 0.000010807873285167379, -1),
(195, 116, 18.223088018851783, 0.000019777500989974936, -1),
(196, 113, -5.489914866613997, -0.000023813494056759253, -1),
(197, 117, 7.258897089658525, 0.000005496942951497373, -1),
(198, 112, 2.692218991644706, -0.00000027460010545925845, -1),
(199, 114, -28.050014571196073, -0.000025274461161981095, -1),
(200, -1, 1.8872685416290986, 0, 1);

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
