package application;

/**
 * 人口と年齢のグラフバー
 * @author  Kikuko Toda
 */
public class PopulationBar {

    /**
     * 年齢
     */
	private final String ageText;
	
    /**
     * 人口
     */
	private final int populationInt;

    /**
     * 年齢と人口のペアの初期化
     * @param String ageText 年齢, String populationText 人口
     */
	public PopulationBar(String ageText, String populationText) {
		this.ageText = ageText;
		populationText = populationText.replace(",","");
		this.populationInt = Integer.parseInt(populationText);
	}

    /**
     * 年齢取得
     * 
     * @return String ageText 年齢
     */
	public String getAgeText() {
		return this.ageText;
	}

    /**
     * 人口取得
     * 
     * @return String populationText 人口
     */
	public int getPopulationInt() {
		return this.populationInt;
	}

}
