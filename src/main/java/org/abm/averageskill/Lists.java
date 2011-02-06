package org.abm.averageskill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lists {
	public static interface F<A, B> {
		public B f(A a);
	}

	public static interface Effect {
		public void go();
	}

	public void apply(Collection<Effect> effects) {
		for (Effect effect : effects) {
			effect.go();
		}
	}

	public static <A, B> List<B> map(F<A, B> f, List<A> list) {
		List<B> output = new ArrayList<B>();
		for (A a : list) {
			output.add(f.f(a));
		}
		return output;
	}

	public static <A> List<A> filter(F<A, Boolean> filter, List<A> list) {
		List<A> output = new ArrayList<A>();
		for (A a : list) {
			if (filter.f(a)) {
				output.add(a);
			}
		}
		return output;
	}

}
