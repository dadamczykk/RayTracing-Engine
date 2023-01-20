package agh.rayTracing.materials;

public enum MaterialType {
    GLASS,
    LIGHT,
    METAL,
    MAT;

    @Override
    public String toString() {
        return switch (this){
            case GLASS -> "Material: glass";
            case MAT -> "Material: mat";
            case METAL -> "Material: metal";
            case LIGHT -> "Material: light";
        };
    }
}
