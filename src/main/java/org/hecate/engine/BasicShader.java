package org.hecate.engine;

public class BasicShader extends Shader {

    private static final BasicShader instance = new BasicShader();


    public static BasicShader getInstance()
    {
        return instance;
    }

    public BasicShader()
    {
        super();

        addVertexShader(ResourceLoader.loadShader("basicVertex.hvs"));
        addFragmentShader(ResourceLoader.loadShader("basicFragment.hfrs"));
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material)
    {
        if(material.getTexture() == null)
          RenderUtils.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
