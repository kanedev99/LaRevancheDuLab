package chimie;

public enum Elements {

	HYDROGEN("H", "Hydrogene", 1, 1, 1, ElementCategory.NON_METAL), 
	HELIUM("He", "Helium", 2, 2, 2, ElementCategory.GAZ_NOBLE),
	LITHIUM("Li", "Lithium", 3, 3, 1, ElementCategory.ALCALIN),
	BERYLLIUM("Be", "Beryllium", 4, 4, 2, ElementCategory.ALCALINO_TERREUX),
	BORE("B", "Bore", 5, 5, 3, ElementCategory.METALLOIDE),
	CARBONE("C", "Carbone", 6, 6, 4, ElementCategory.NON_METAL),
	AZOTE("N", "Azote", 7, 7, 5, ElementCategory.NON_METAL),
	OXYGENE("O", "Oxygene", 8, 8, 6, ElementCategory.NON_METAL),
	FLUOR("F", "Fluor", 9, 9, 7, ElementCategory.HALOGENE),
	NEON("Ne", "Neon", 10, 10, 8, ElementCategory.GAZ_NOBLE),
	SODIUM("Na", "Sodium", 11, 11, 1, ElementCategory.ALCALIN),
	MAGNESIUM("Mg", "Magnesium", 12, 12, 2, ElementCategory.ALCALINO_TERREUX),
	ALUMINIUM("Al", "Aluminium", 13, 13, 3, ElementCategory.METAL_PAUVRE),
	SILICIUM("Si", "Silicium", 14, 14, 4, ElementCategory.METALLOIDE),
	PHOSPHORE("P", "Phosphore", 15, 15, 5, ElementCategory.NON_METAL),
	SOUFRE("S", "Soufre", 16, 16, 6, ElementCategory.NON_METAL),
	CHLORE("Cl", "Chlore", 17, 17, 7, ElementCategory.HALOGENE),
	ARGON("Ar", "Argon", 18, 18, 8, ElementCategory.GAZ_NOBLE),
	POTASSIUM("K", "Potassium", 19, 19, 1, ElementCategory.ALCALIN),
	CALCIUM("Ca", "Calcium", 20, 20, 2, ElementCategory.ALCALINO_TERREUX),
	SCANDIUM("Sc", "Scandium", 21, 21, 3,ElementCategory.METAL_TRANSITION),
	TITANE("Ti", "Titanium", 22, 22, 4, ElementCategory.METAL_TRANSITION),
	VANADIUM("V", "Vanadium", 23, 23 , 5, ElementCategory.METAL_TRANSITION),
	CHROME("Cr", "Chrome", 24, 24, 6, ElementCategory.METAL_TRANSITION),
	MANGANESE("Mn", "Manganese", 25, 25, 7, ElementCategory.METAL_TRANSITION),
	FER("Fe", "Fer", 26, 26, 8, ElementCategory.METAL_TRANSITION),
	COBALT("Co", "Cobalt", 27, 27, 9, ElementCategory.METAL_TRANSITION),
	NICKEL("Ni", "Nickel", 28, 28, 10, ElementCategory.METAL_TRANSITION),
	CUIVRE("Cu", "Cuivre", 29, 29, 1, ElementCategory.METAL_TRANSITION),
	ZINC("Zn", "Zinc", 30, 30, 2, ElementCategory.METAL_PAUVRE),
	GALLIUM("Ga", "Galium", 31, 31, 3, ElementCategory.METAL_PAUVRE),
	GERMANIUM("Ge", "Germanium", 32, 32, 4, ElementCategory.METALLOIDE),
	ARSENIC("As", "Arsenic", 33, 33, 5, ElementCategory.METALLOIDE),
	SELENIUM("Se", "Selenium", 34, 34, 6, ElementCategory.NON_METAL),
	BROME("Br", "Brome", 35, 35, 7, ElementCategory.HALOGENE),
	KRYPTON("Kr", "Krypton", 36, 36, 8, ElementCategory.GAZ_NOBLE),
	RUBIDIUM("Rb", "Rubidium", 37, 37, 1, ElementCategory.ALCALIN),
	STRONTIUM("Sr", "Strontium", 38, 38, 2, ElementCategory.ALCALINO_TERREUX),
	YTTRIUM("Y", "Yttrium", 39, 39, 3, ElementCategory.METAL_TRANSITION),
	ZIRCONIUM("Zr", "Zirconium", 40, 40, 4, ElementCategory.METAL_TRANSITION),
	NIOBIUM("Nb", "Niobium", 41, 41, 5, ElementCategory.METAL_TRANSITION),
	MOLYBDENE("Mo", "Molybdene", 42, 42, 6, ElementCategory.METAL_TRANSITION),
	TECHNETIUM("Tc", "Technetium", 43, 43, 7, ElementCategory.METAL_TRANSITION),
	RUTHENIUM("Ru", "Ruthenium", 44, 44, 8, ElementCategory.METAL_TRANSITION),
	RHODIUM("Rh", "Rhodium", 45, 45, 9, ElementCategory.METAL_TRANSITION),
	PALLADIUM("Pd", "Palladium", 46, 46, 10, ElementCategory.METAL_TRANSITION),
	ARGENT("Ag", "Argent", 47, 47, 1, ElementCategory.METAL_TRANSITION),
	CADMIUM("Cd", "Cadmium", 48, 48, 2, ElementCategory.METAL_PAUVRE),
	INDIUM("In", "Indium", 49, 49, 3, ElementCategory.METAL_PAUVRE),
	ETAIN("Sn", "Etain", 50, 50, 4, ElementCategory.METAL_PAUVRE),
	ANTIMOINE("Sb", "Antimoine", 51, 51, 5, ElementCategory.METALLOIDE),
	TELLURE("Te", "Tellure", 52, 52, 6, ElementCategory.METALLOIDE),
	IODE("I", "Iode", 53, 53, 7, ElementCategory.HALOGENE),
	XEON("Xe", "Xeon", 54, 54, 8, ElementCategory.GAZ_NOBLE),
	CESIUM("Cs", "Cesium", 55, 55, 1, ElementCategory.ALCALIN),
	BARYUM("Ba", "Baryum", 56, 56, 2, ElementCategory.ALCALINO_TERREUX),
	HAFNIUM("Hf", "Hafnium", 72, 72, 4, ElementCategory.METAL_TRANSITION),
	TANTALE("Ta", "Tantale", 73, 73, 5, ElementCategory.METAL_TRANSITION),
	TUNGSTENE("W", "Tungstene", 74, 74, 6, ElementCategory.METAL_TRANSITION),
	RHENIUM("Re", "Rhenium", 75, 75, 7, ElementCategory.METAL_TRANSITION),
	OSMIUM("Os", "Osmium", 76, 76, 8, ElementCategory.METAL_TRANSITION),
	IRIDIUM("Ir", "Iridium", 77, 77, 9, ElementCategory.METAL_TRANSITION),
	PLATINE("Pt", "Platine", 78, 78, 10, ElementCategory.METAL_TRANSITION),
	OR("Au", "Or", 79, 79, 1, ElementCategory.METAL_TRANSITION),
	MERCURE("Hg", "Mercure", 80, 80, 2, ElementCategory.METAL_PAUVRE),
	THALLIUM("Tl", "Thallium", 81, 81, 3, ElementCategory.METAL_PAUVRE),
	PLOMB("Pb", "Plomb", 82, 82, 4, ElementCategory.METAL_PAUVRE),
	BISMUTH("Bi", "Bismuth", 83, 83, 5, ElementCategory.METAL_PAUVRE),
	POLONIUM("Po", "Polonium", 84, 84, 6, ElementCategory.METAL_PAUVRE),
	ASTATE("At", "Astate", 85, 85, 7, ElementCategory.METALLOIDE),
	RADON("Rn", "Radon", 86, 86, 8, ElementCategory.GAZ_NOBLE),
	FRANCIUM("Fr", "Francium", 87, 87, 1, ElementCategory.ALCALIN),
	RADIUM("Ra", "Radium", 88, 88, 2, ElementCategory.ALCALINO_TERREUX),
	RUTHERFORDIUM("Rf", "Rutherfordium", 104, 104, 4, ElementCategory.METAL_TRANSITION),
	DUBNIUM("Db", "Dubnium", 105, 105, 5, ElementCategory.METAL_TRANSITION),
	SEABORGIUM("Sg", "Seaborgium", 106, 106, 6, ElementCategory.METAL_TRANSITION),
	BOHRIUM("Bh", "Bohrium", 107, 107, 7, ElementCategory.METAL_TRANSITION),
	HASSIUM("Hs", "Hassium", 108, 108, 8, ElementCategory.METAL_TRANSITION),
	MEITNERIUM("Mt", "Meitnerium", 109, 109, 9, ElementCategory.NON_CLASSE),
	DARMSTADTIUM("Ds", "Darmstadtium", 110, 110, 110, ElementCategory.NON_CLASSE),
	ROENTGENIUM("Rg", "Roentgenium", 111, 111, 1, ElementCategory.NON_CLASSE),
	COPERNICIUM("Cn", "Copernicium", 112, 112, 2, ElementCategory.METAL_TRANSITION),
	NIHONIUM("Nh", "Nihonium", 113, 113, 3, ElementCategory.NON_CLASSE),
	FLEROVIUM("Fl", "Flerovium", 114, 114, 4, ElementCategory.NON_CLASSE),
	MOSCOVIUM("Mc", "Moscovium", 115, 115, 5, ElementCategory.NON_CLASSE),
	LIVERMORIUM("Lv", "Livermorium", 116, 116, 6, ElementCategory.NON_CLASSE),
	TENNESSE("Ts", "Tennesse", 117, 117, 7, ElementCategory.NON_CLASSE),
	ORGANESSON("Og", "Organesson", 118, 118, 8, ElementCategory.NON_CLASSE);
	
	// Définir les propriétés pour chaque élément
	private final String symbole;
	private final String name;
	private final int numeroAtomique;
	private final int nbElectrons;
	private final int nbElectronsVal;
	private final ElementCategory categorie;

	// Définir une méthode toString pour l'affichage dans la grille
	public String toString() {
		return symbole;
	}

	// Definir des constructeurs pour initialiser les proprietes
	private Elements(String symbole, String name, int numeroAtomique, int nbElectrons, int nbElectronsVal, ElementCategory categorie) {
        this.symbole = symbole;
        this.name = name;
        this.numeroAtomique = numeroAtomique;
        this.nbElectrons = nbElectrons;
        this.nbElectronsVal = nbElectronsVal;
        this.categorie = categorie;
    }
	
	// Définir des accesseurs pour les propriétés
	public String getSymbol() {
		return symbole;
	}

	public String getName() {
		return name;
	}

	public int getNumeroAtomique() {
		return numeroAtomique;
	}

	public int getElectrons() {
		return nbElectrons;
	}
	
	public int getElectronsVal() {
		return nbElectronsVal;
	}

	public ElementCategory getCategory() {
		return categorie;
	}
}

