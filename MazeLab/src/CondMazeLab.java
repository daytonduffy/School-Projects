
public class CondMazeLab extends Maze {
    public static void main(String[] args) {
        CondMazeLab myMaze = new CondMazeLab();
    }


    public void step() {
        // Actual hard movement part
        if (puss.isFacingWall()) {
            puss.right();
            if (puss.isFacingWall()) {
                puss.left();
                puss.left();
                if (puss.isFacingWall()) {
                    puss.left();
                }
            }
        } else if (puss.isFacingGully()) {
            if (puss.isTipToeing()) {
                puss.stopTipToe();
            }
            if (puss.isInBoots()) {
                puss.takeOffBoots();
            }
            puss.jump();
            puss.right();
            if (!puss.isFacingDog() && !puss.isFacingMud() && !puss.isFacingWall()) {
                if (puss.isFacingGully()) {
                    if (puss.isTipToeing()) {
                        puss.stopTipToe();
                    }
                    if (puss.isInBoots()) {
                        puss.takeOffBoots();
                    }
                    puss.jump();
                }
                puss.forward();
            } else {
                puss.left();
            }
        } else if (puss.isFacingGully()) {
            if (puss.isTipToeing()) {
                puss.stopTipToe();
            }
            if (puss.isInBoots()) {
                puss.takeOffBoots();
            }
            puss.jump();
            puss.right();
            if (!puss.isFacingDog() && !puss.isFacingMud() && !puss.isFacingWall()) {
                if (puss.isFacingGully()) {
                    if (puss.isTipToeing()) {
                        puss.stopTipToe();
                    }
                    if (puss.isInBoots()) {
                        puss.takeOffBoots();
                    }
                    puss.jump();
                }
                puss.forward();
            } else {
                puss.left();
            }
        } else if (puss.isFacingMud()) {
            if (puss.isTipToeing()) {
                puss.stopTipToe();
            }
            if (!puss.isInBoots()) {
                puss.putOnBoots();
            }
            puss.forward();
            puss.right();
            if (!puss.isFacingDog() && !puss.isFacingGully() && !puss.isFacingMud()
                && !puss.isFacingWall()) {
                puss.forward();
            } else {
                puss.left();
            }
        } else if (puss.isFacingDog()) {
            if (puss.isInBoots()) {
                puss.takeOffBoots();
            }
            if (!puss.isTipToeing()) {
                puss.startTipToe();
            }
            puss.forward();
            puss.right();
            if (!puss.isFacingDog() && !puss.isFacingGully() && !puss.isFacingMud()
                && !puss.isFacingWall()) {
                puss.forward();
            } else {
                puss.left();
            }
        } else {
            puss.forward();
            puss.right();
            if (puss.isFacingGully()) {
                if (puss.isTipToeing()) {
                    puss.stopTipToe();
                }
                if (puss.isInBoots()) {
                    puss.takeOffBoots();
                }
                puss.jump();
            } else if (!puss.isFacingDog() && !puss.isFacingGully() && !puss.isFacingMud()
                && !puss.isFacingWall()) {
                puss.forward();
            } else {
                puss.left();
            }
        }
    }



    public CondMazeLab() {
        super(true);
    }
}
