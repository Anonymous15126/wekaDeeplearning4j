/*
 * WekaDeeplearning4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WekaDeeplearning4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WekaDeeplearning4j.  If not, see <https://www.gnu.org/licenses/>.
 *
 * LeNet.java
 * Copyright (C) 2017-2018 University of Waikato, Hamilton, New Zealand
 */

package weka.dl4j.zoo;

import org.deeplearning4j.nn.conf.CacheMode;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import weka.dl4j.Preferences;
import weka.dl4j.enums.PretrainedType;

/**
 * A WEKA version of DeepLearning4j's LeNet ZooModel.
 *
 * @author Steven Lang
 * @author Rhys Compton
 */
public class Dl4jLeNet extends AbstractZooModel {

  /**
   * Unique ID for this version of the model.
   */
  private static final long serialVersionUID = 1656653625527283532L;

  /**
   * Instantiate the model.
   */
  public Dl4jLeNet() {
    setPretrainedType(PretrainedType.MNIST);
    setNumFExtractOutputs(500);
    setFeatureExtractionLayer("7");
    setOutputLayer("8");
    setExtraLayersToRemove(new String[] {"9"});
  }

  @Override
  public ComputationGraph init(int numLabels, long seed, int[] shape, boolean filterMode) {
    org.deeplearning4j.zoo.model.LeNet net = org.deeplearning4j.zoo.model.LeNet.builder()
        .cacheMode(CacheMode.NONE)
        .workspaceMode(Preferences.WORKSPACE_MODE)
        .inputShape(shape)
        .numClasses(numLabels)
        .build();

    ComputationGraph defaultNet = ((MultiLayerNetwork) net.init()).toComputationGraph();

    return initZooModel(net, defaultNet, seed, numLabels, filterMode);
  }

  @Override
  public int[] getInputShape() {
    if (isPretrained())
      return new int[] {1, 28, 28};

    return org.deeplearning4j.zoo.model.LeNet.builder().build().metaData().getInputShape()[0];
  }

  /**
   * Get the current variation of the zoo model (e.g., Resnet50 or Resnet101)
   *
   * @return Variation
   */
  @Override
  public Enum getVariation() {
    return null;
  }

  @Override
  public ImagePreProcessingScaler getImagePreprocessingScaler() {
    return null;
  }
}
