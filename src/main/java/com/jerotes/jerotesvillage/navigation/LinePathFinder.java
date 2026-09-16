package com.jerotes.jerotesvillage.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LinePathFinder extends PathFinder {
    public LinePathFinder(NodeEvaluator nodeEvaluator, int n) {
        super(nodeEvaluator, n);
    }

    @Nullable
    @Override
    public Path findPath(PathNavigationRegion pathNavigationRegion, Mob mob, Set<BlockPos> set, float f, int n, float f2) {
        Path path = super.findPath(pathNavigationRegion, mob, set, f, n, f2);
        return path == null ? null : new Paths(path);
    }

    static class Paths extends Path {
        public Paths(Path path) {
            super(copyPath(path), path.getTarget(), path.canReach());
        }

        @Override
        public Vec3 getEntityPosAtNode(Entity entity, int n) {
            Node point = this.getNode(n);
            double offset = Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
            double d0 = point.x + offset;
            double d1 = point.y;
            double d2 = point.z + offset;
            return new Vec3(d0, d1, d2);
        }

        private static List<Node> copyPath(Path path) {
            int nodeCount = path.getNodeCount();
            List<Node> list = new ArrayList<>(nodeCount);
            for (int i = 0; i < nodeCount; i++) {
                list.add(path.getNode(i));
            }
            return list;
        }
    }
}