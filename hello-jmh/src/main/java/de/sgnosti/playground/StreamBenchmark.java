/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.sgnosti.playground;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.special.Gamma;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StreamBenchmark {

	public interface Action {
		void perform();
	}

	public class ArrayListStreamBenchmark implements Action {
		private final ArrayList<Double> doubleArrayList = new ArrayList<>(1_000_000);
		private double result;

		public ArrayListStreamBenchmark() {
			for (int i = 0; i < doubleArrayList.size(); i++) {
				doubleArrayList.add(i, (double) i);
			}
		}

		@Benchmark
		@Override
		public void perform() {
			result = doubleArrayList.stream().map((x) -> Gamma.gamma(x)).reduce(0.0, Math::max);
		}

		public double getResult() {
			return result;
		}
	}

	public class ArrayListParallelStreamBenchmark implements Action {
		private final ArrayList<Double> doubleArrayList = new ArrayList<>(1_000_000);
		private double result;

		public ArrayListParallelStreamBenchmark() {
			for (int i = 0; i < doubleArrayList.size(); i++) {
				doubleArrayList.add(i, (double) i);
			}
		}

		@Benchmark
		@Override
		public void perform() {
			result = doubleArrayList.stream().parallel().map((x) -> Gamma.gamma(x)).reduce(0.0, Math::max);
		}

		public double getResult() {
			return result;
		}
	}
    
    public static void main(String[] args) {
		Options options = new OptionsBuilder().include(".*Benchmark.*").timeUnit(TimeUnit.MILLISECONDS).build();
		Runner runner = new Runner(options);
		try {
			runner.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
