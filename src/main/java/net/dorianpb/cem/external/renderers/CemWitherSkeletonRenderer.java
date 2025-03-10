package net.dorianpb.cem.external.renderers;

import net.dorianpb.cem.external.models.CemSkeletonModel;
import net.dorianpb.cem.internal.api.CemRenderer;
import net.dorianpb.cem.internal.models.CemModelRegistry;
import net.dorianpb.cem.internal.util.CemRegistryManager;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class CemWitherSkeletonRenderer extends WitherSkeletonEntityRenderer implements CemRenderer{
	private final CemModelRegistry registry;
	
	public CemWitherSkeletonRenderer(EntityRendererFactory.Context context){
		super(context);
		this.registry = CemRegistryManager.getRegistry(getType());
		try{
			this.model = new CemSkeletonModel(registry);
			if(registry.hasShadowRadius()){
				this.shadowRadius = registry.getShadowRadius();
			}
			this.features.replaceAll((feature) -> {
				if(feature instanceof ArmorFeatureRenderer){
					return new ArmorFeatureRenderer<>(this,
					                                  new CemSkeletonModel(CemRegistryManager.getArmorRegistry(getType()), 0.5F),
					                                  new CemSkeletonModel(CemRegistryManager.getArmorRegistry(getType()), 1.0F)
					);
				}
				else{
					return feature;
				}
			});
		} catch(Exception e){
			modelError(e);
		}
	}
	
	private static EntityType<? extends Entity> getType(){
		return EntityType.WITHER_SKELETON;
	}
	
	@Override
	public String getId(){
		return getType().toString();
	}
	
	@Override
	public Identifier getTexture(AbstractSkeletonEntity entity){
		if(this.registry != null && this.registry.hasTexture()){
			return this.registry.getTexture();
		}
		return super.getTexture(entity);
	}
}