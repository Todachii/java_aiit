package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 人口データを読み込むスライダー
 * @author  Kikuko Toda
 */
public class SliderUtil {

	/**
     * csvの行リスト
     */
	private List<String> lines = new ArrayList<String>();
    
	/**
     * 人口セット
     */
	private HashMap<Integer, Population> populations = new HashMap<Integer, Population>();
    
	/**
     * スライダーが指す年のリスト
     */
	private List<String> yearIndexes = new ArrayList<String>();

    /**
     * 初期化　csvファイル読み込み
     * @param String fileName csvファイル名, String charset 文字コード
     * @throws IOException 
     */
	public SliderUtil(InputStream input, String charset) throws IOException {
		Reader r = new InputStreamReader(input, charset);
		
		BufferedReader br = new BufferedReader(r);
		String line = br.readLine();
		String[] cs = line.split("¥t");
		this.yearIndexes = Arrays.asList(cs);
		
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		
		br.close();
	}
	
    /**
     * スライダーが指す年のデータを取得
     * 
     * @return List<String> yearIndexes スライダーが指す年のデータリスト
     */
	public List<String> readYearIndex() {
		return this.yearIndexes;
	}

	/**
	 * スライダーが指す年の人口セットを返す
	 * @param int yearIndex スライダーが指す年
	 * @return Population population 人口セット
	 */
	public Population readPopulation(int yearIndex) {
		
		// 既に読み込んでいれば人口マップから出す
		if (this.populations.containsKey(yearIndex)) {
			return this.populations.get(yearIndex);
		}

		Population population = new Population();
		// CSV読み込み
		String line;
		for ( int i = 0; i < this.lines.size(); ++i ) {
			line = lines.get(i);
			String[] cs = line.split("¥t");
			population.addBar(cs[0], cs[yearIndex]);
		}
		// 人口マップにセット
		this.populations.put(yearIndex, population);
		return population;
	}


}
