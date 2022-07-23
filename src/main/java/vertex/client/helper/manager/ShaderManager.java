

package vertex.client.helper.manager;

import vertex.client.helper.render.shader.Shader;

public class ShaderManager {
    public static final Shader BLUR = Shader.create("blur", managedShaderEffect -> managedShaderEffect.setUniformValue("radius", 5f));

}
