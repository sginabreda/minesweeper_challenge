package com.sginabreda.minesweeper.util;

import com.google.common.collect.Lists;
import com.sginabreda.minesweeper.infrastructure.repository.model.CellModel;

import java.util.Arrays;
import java.util.List;

public class ListUtil {

	public static CellModel[][] generateGrid(List<CellModel> elements, Integer rows) {
		return Lists.partition(elements, rows)
				.stream()
				.map(lst -> lst.toArray(CellModel[]::new))
				.map(a -> Arrays.copyOf(a, rows))
				.toArray(CellModel[][]::new);
	}
}
