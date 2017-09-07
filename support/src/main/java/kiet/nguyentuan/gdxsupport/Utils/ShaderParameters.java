package kiet.nguyentuan.gdxsupport.Utils;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Pass data to your shader programs
 */

public abstract class ShaderParameters {
    /**
     *Pass uniforms to shader program
     * @param shaderProgram the custom shader program which was set to Stage
     */
    public abstract void passShaderUniforms(ShaderProgram shaderProgram);
}
