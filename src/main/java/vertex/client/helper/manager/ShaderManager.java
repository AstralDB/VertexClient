/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.helper.manager;

import vertex.client.helper.render.shader.Shader;

public class ShaderManager {
    public static final Shader BLUR = Shader.create("blur", managedShaderEffect -> managedShaderEffect.setUniformValue("radius", 5f));

}
