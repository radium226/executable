package radium.executable.example;

import org.kohsuke.args4j.Option;

import com.google.inject.Inject;

public class Foo {

	@Inject
	@Option(name = "--name", required = true)
	private String bar;
	
	public Foo() {
		super();
	}
	
	public String getBar() {
		return bar;
	}
	
}
