package io.github.madis0.mixin;

import io.github.madis0.Calculations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow protected abstract PlayerEntity getCameraPlayer();

	@Shadow private int scaledWidth;
	@Shadow private int scaledHeight;

	private MinecraftClient client;
	private MatrixStack stack;
	private PlayerEntity playerEntity;

	boolean showArrowCount = true;
	boolean showProgressBar = true;

	int countStartX;
	int countStartY;
	int progStartW;
	int progEndW;
	int progStartH;
	int progEndH;
	int backgroundColor;

	@Inject(at = @At("TAIL"), method = "render")
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		client = MinecraftClient.getInstance();
		stack = matrixStack;
		playerEntity = this.getCameraPlayer();

		countStartX = (this.scaledWidth / 2) + 2;
		countStartY = (this.scaledHeight / 2);

		progStartW = countStartX - 12;
		progStartH = countStartY + 10;
		progEndW = progStartW + 18;
		progEndH = progStartH + 1;

		backgroundColor = 0xFF000000;

		if(showArrowCount) arrowCount();
		if(showProgressBar) progressBar();
	}

	private void arrowCount(){
		int color = 0xFFFFFFFF;
		int textX = countStartX;
		int textY = countStartY;

		client.textRenderer.drawWithShadow(stack, Calculations.useSubscript("99"), textX, textY, color);
	}

	private void progressBar(){
		int xpLevel = playerEntity.experienceLevel;
		int maxXp = 183;
		int xp = (int)(playerEntity.experienceProgress * maxXp);

		int xpColor = 0xFF00C853;
		int relativeEndW = Calculations.relativeW(progStartW, progEndW, xp, maxXp);

		DrawableHelper.fill(stack, progStartW, progStartH, progEndW, progEndH, backgroundColor);
		DrawableHelper.fill(stack, progStartW, progStartH, relativeEndW, progEndH, xpColor);
	}

	private int baseRelativeEndW(int value, int total){
		return Calculations.relativeW(progStartW, progEndW, value, total);
	}

	private int baseRelativeStartW(int value, int total){
		return Calculations.relativeW(progEndW, progStartW, value, total);
	}
}