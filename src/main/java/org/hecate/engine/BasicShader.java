package org.hecate.engine;

public class BasicShader extends Shader {

    public BasicShader()
    {
        super();

        addVertexShader(ResourceLoader.loadShader("basicVertex.hvs"));
        addFragmentShader(ResourceLoader.loadShader("basicFragment.hfrs"));
        compileShader();

        addUniform("transform");
    }

    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix)
    {
        setUniform("transform", projectedMatrix);
    }
}
