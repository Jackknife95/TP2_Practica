package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.MostCrowdedStrategyBuilder;
import simulator.factories.MoveAllStrategyBuilder;
import simulator.factories.MoveFirstStrategyBuilder;
import simulator.factories.NewCityRoadEventBuilder;
import simulator.factories.NewInterCityRoadEventBuilder;
import simulator.factories.NewJunctionEventBuilder;
import simulator.factories.NewVehicleEventBuilder;
import simulator.factories.RoundRobinStrategyBuilder;
import simulator.factories.SetContClassEventBuilder;
import simulator.factories.SetWeatherEventBuilder;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.TrafficSimulator;
import simulator.view.MainWindow;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;
    private static int ticks;
    private static String _guiMode = null;
	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseGuiOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);
			
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator?s main loop (default value is 10)").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Simulastion gui mode").build());
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if(_guiMode.equals("gui")) {
			
		}
		else {
			if (_inFile == null) {
				throw new ParseException("An events file is missing");
			}
		}
		
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
	
	String s = line.getOptionValue("t");

		try {
			if(s != null) {
				ticks = Integer.parseInt(s);
			}
			else {
				ticks = _timeLimitDefaultValue;
			}			
		}
		catch (NumberFormatException e) { 
			throw new NumberFormatException("Not valid Number in arguments");
		}
	}
	
	private static void parseGuiOption(CommandLine line) throws ParseException {
		
		_guiMode= "gui";
		
		String s = line.getOptionValue("m").toLowerCase();
		

			if(s!=null) {
				
				if(s.equals("gui")) {
					
				}
				
				else if(s.equals("console")) {
					_guiMode= "console";
				}
				
				else {
					throw new IllegalArgumentException(s + " is not a valid gui mode");
				}
			}
		
	}
	
	private static void initFactories() {

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add( new RoundRobinStrategyBuilder() );
		lsbs.add( new MostCrowdedStrategyBuilder() );
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add( new MoveFirstStrategyBuilder() );
		dqbs.add( new MoveAllStrategyBuilder() );
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		ArrayList<Builder<Event>> ebs = new ArrayList<>();
		ebs.add( new NewJunctionEventBuilder(lssFactory,dqsFactory) );
		ebs.add( new NewCityRoadEventBuilder() );
		ebs.add( new NewInterCityRoadEventBuilder() );
		ebs.add( new NewVehicleEventBuilder() );
		ebs.add( new SetWeatherEventBuilder() );
		ebs.add( new SetContClassEventBuilder() );
		Factory<Event> eventsFactory = new BuilderBasedFactory<>(ebs);
		
		_eventsFactory = eventsFactory;

	}

	private static void startBatchMode() throws IOException {
		
		InputStream f = new FileInputStream(new File(_inFile));
		
		TrafficSimulator t = new TrafficSimulator();
		
		Controller c = new Controller(t, _eventsFactory);
				
		c.loadEvents(f);
		
		f.close();
		
		if(_outFile!= null) {
			OutputStream o = new FileOutputStream(new File(_outFile));
			c.run(ticks, o);
			o.close();
		}		
		else {
			c.run(ticks, System.out);
		}		
		
	}
	
	private static void startGUIMode() throws IOException {
		
		TrafficSimulator t = new TrafficSimulator();
		Controller c = new Controller(t, _eventsFactory);
		if(_inFile!=null) {
			InputStream f = new FileInputStream(new File(_inFile));
			c.loadEvents(f);
			f.close();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			new MainWindow(c);
			}
		});
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if(_guiMode.equals("gui")) {
			startGUIMode();
		}
		if(_guiMode.equals("console")) {
			startBatchMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
