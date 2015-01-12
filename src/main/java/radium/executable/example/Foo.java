package radium.executable.example;

import com.google.inject.Inject;

import org.kohsuke.args4j.Option;

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
