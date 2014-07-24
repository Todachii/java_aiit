package application;

import java.util.LinkedList;

/**
 * 人口データ
 * @author  Kikuko Toda
 */
public class Population {

    /**
     * グラフバーのリスト
     */
	private final LinkedList<PopulationBar> bars = new LinkedList<PopulationBar>();

    /**
     * グラフバーのリスト取得
     * 
     * @return LinkedList<PopulationBar> bars グラフバーのリスト
     */
	public LinkedList<PopulationBar> getBars() {
		return bars;
	}
	
    /**
     * グラフバーをグラフバーのリストに追加
     * 
     * @param String age 年齢, String population 人口
     */
	public void addBar(String age, String population) {
		bars.add(new PopulationBar(age, population));
	}

}
