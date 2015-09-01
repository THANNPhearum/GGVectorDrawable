/*
 * Copyright 2015 Trello, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trello.victor

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs
import org.gradle.api.tasks.incremental.InputFileDetails

/**
 * Task that rasterizes SVGs into PNGs.
 */
class RasterizeTask extends DefaultTask {

    /**
     * The input SVGs.
     */
    @InputFiles
    FileCollection sources

    /**
     * The output directory.
     */
    @OutputDirectory
    File outputDir

    /**
     * The densities to scale assets for.
     */
    List<Density> includeDensities

    /**
     * There is a bug determining whether this task is up-to-date with
     * `includeDensities`: because it is a List<Enum>, it fails when you
     * have the daemon running the build. This method is provided as a
     * workaround to test up-to-dateness, but should never actually used
     * beyond that.
     *
     * References:
     * - https://discuss.gradle.org/t/custom-task-never-up-to-date-when-running-daemon/9525
     * - https://issues.gradle.org/browse/GRADLE-3018
     */
    @Input
    List<String> getDensitiesWorkaround() {
        includeDensities.collect { it.toString() }
    }

    /**
     * The DPI to use for relative-sized SVGs.
     */
    @Input
    int baseDpi

    @TaskAction
    def rasterize(IncrementalTaskInputs inputs) {
        // If the whole thing isn't incremental, delete the build folder (if it exists)
        if (!inputs.isIncremental() && outputDir.exists()) {
            logger.debug("SVG rasterization is not incremental; deleting build folder and starting fresh!")
            outputDir.delete()
        }

        List<File> svgFiles = []
        inputs.outOfDate { InputFileDetails change ->
            logger.debug("$change.file.name out of date; converting")
            svgFiles.add change.file
        }

        // Make sure all output directories exist
        includeDensities.each { Density density ->
            File resDir = getResourceDir(density)
            resDir.mkdirs()
        }

        Converter converter = new Converter()
        svgFiles.each { File svgFile ->
            SVGResource svgResource = new SVGResource(svgFile, baseDpi)

            includeDensities.each { Density density ->
                File destination = new File(getResourceDir(density), getDestinationFile(svgFile.name))
                converter.transcode(svgResource, density, destination)
                logger.info("Converted $svgFile to $destination")
            }
        }

        inputs.removed { change ->
            logger.debug("$change.file.name was removed; removing it from generated folder")

            includeDensities.each { Density density ->
                File resDir = getResourceDir(density)
                File file = new File(resDir, getDestinationFile(change.file.name))
                file.delete()
            }
        }
    }

    File getResourceDir(Density density) {
        return new File(outputDir, "/drawable-${density.name().toLowerCase()}")
    }

    String getDestinationFile(String name) {
        int suffixStart = name.lastIndexOf '.'
        return suffixStart == -1 ? name : "${name.substring(0, suffixStart)}.png"
    }
}