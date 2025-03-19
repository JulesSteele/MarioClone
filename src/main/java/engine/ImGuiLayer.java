package engine;

import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiFreeTypeBuilderFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

public class ImGuiLayer
{
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private long glfwWindow;

    public ImGuiLayer(long glfwWindow)
    {
        this.glfwWindow = glfwWindow;
    }

    public void update(Scene currentScene)
    {
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

        // All ImGui code should go here
        currentScene.sceneImgui();
        ImGui.showDemoWindow();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    public void initImGui()
    {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        imGuiGlfw.init(glfwWindow, true);
        imGuiGl3.init("#version 330 core");

        this.initFonts(io);
    }

    private void initFonts(final ImGuiIO io) {
        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        // Glyphs could be added per-font as well as per config used globally like here
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

        // Fonts merge example
        fontConfig.setPixelSnapH(true);
        fontAtlas.addFontFromFileTTF("assets/fonts/Ubuntu-Regular.ttf", 16, fontConfig);

        fontConfig.destroy();

        // Use freetype instead of stb_truetype to build a fonts texture
        fontAtlas.setFlags(ImGuiFreeTypeBuilderFlags.LightHinting);
        fontAtlas.build();
    }

    public void destroy()
    {
        imGuiGlfw.shutdown();
        imGuiGl3.shutdown();
        ImGui.destroyContext();
    }
}
