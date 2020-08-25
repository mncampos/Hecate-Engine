package org.hecate.engine;

public class PhongShader extends Shader {
    private static final PhongShader instance = new PhongShader();


    public static PhongShader getInstance()
    {
        return instance;
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    private static Vector3f ambientLight;

    public PhongShader()
    {
        super();

        addVertexShader(ResourceLoader.loadShader("phongVertex.hvs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.hfrs"));
        compileShader();

        addUniform("transform");
        addUniform("baseColor");
        addUniform("ambientLight");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() == null)
            RenderUtils.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("baseColor", material.getColor());
        setUniform("ambientLight", ambientLight);
    }
}
